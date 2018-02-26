package dsl

import java.util.List;


abstract class SimulationSensorBasescript extends Script {	
	
	
    def duration(int ticks){
    	((SimulationSensorBinding)this.getBinding()).getSimulationSensorModel().setTime(ticks)
    }


    def zone(String name){
    	((SimulationSensorBinding)this.getBinding()).getSimulationSensorModel().createZone(name)
    }

    def lot(int nbSensor){
    	[using: {behavior ->
    		[on: {zone->
    			((SimulationSensorBinding)this.getBinding()).getSimulationSensorModel().addSensorToZone(nbSensor,behavior,zone)
    		}]
    	}]
    }

    def lawRandom(String name){
    	[between: {n ->
    		[and: {m->
    			((SimulationSensorBinding)this.getBinding()).getSimulationSensorModel().createLawRandom(name,n,m)
    		}]
    	}]
    }

    def lawPolynomial(String name){
    	[value: {n -> 
    		((SimulationSensorBinding)this.getBinding()).getSimulationSensorModel().createLawPolynomial(name,n)
    		}]
    }
    

	
	def sensor(String name){
		[using: {law ->
			[on: {zone->
				((SimulationSensorBinding)this.getBinding()).getSimulationSensorModel().createSensor(name,law,zone)
			}]

		}]
	}

	// disable run method while running
	int count = 0
	abstract void scriptBody()
	def run() {
		if(count == 0) {
			count++
			scriptBody()
		} else {
			println "Run method is disabled"
		}
	}

	def go(String n) { 
		((SimulationSensorBinding)this.getBinding()).getSimulationSensorModel().run(n)
	}

	def writeOn(String type){
		((SimulationSensorBinding)this.getBinding()).getSimulationSensorModel().writeType(type)
	}

	def aggregate(String name){
		((SimulationSensorBinding)this.getBinding()).getSimulationSensorModel().aggregateZone(name)
	}


	def applicatelow(String sensor) {
		[from: { file ->
			[to: { output ->
				((SimulationSensorBinding) this.getBinding()).getSimulationSensorModel().Reapplicate(sensor, file, output)
			}]
		}]
	}
}
