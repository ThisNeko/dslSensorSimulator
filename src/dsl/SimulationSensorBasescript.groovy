package dsl

import java.util.List;


abstract class SimulationSensorBasescript extends Script {	
	
	
    def time(int ticks){
    	((SimulationSensorBinding)this.getBinding()).getSimulationSensorModel().setTime(ticks)
    }

    def zone(String name){
    	[nbRandom: {n -> 
    		[nbNeperien: {nb ->
    			((SimulationSensorBinding)this.getBinding()).getSimulationSensorModel().createZone(name,n,nb) 
    		}]
    			
    	}]
    }
    

	
	def sensor(String name){
		((SimulationSensorBinding)this.getBinding()).getSimulationSensorModel().createSensor(name)
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
}
