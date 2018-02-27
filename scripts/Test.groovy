// Définition




duration 10




lawPolynomial "poly" value ([1.0d,2.0d,3.0d])


lawPolynomial "poly2" value ([1.0d,2.0d,1.0d])



lawFunction "fonction" , {
	t ->
	if (t < 10) return 1
	if (t > 10 && t < 20) return 2
	if (t > 20) return 3
	return 4
}


lawRandom "random" between 10 and 20


zone "parking"

aggregate "parking" using "min"


sensor "try" using "poly" on "classroom_O+106"
sensor "rand" using "random" on "roof_E"
sensor "test" using "poly" on "parking"
sensor "test2" using "fonction" on "parking"

lot 3 using "random" on "roof_E"


aggregate "roof_E" using "max"



writeOn "csv"
//writeOn "influxDB"
//writeOn "terminal"

go "Simulation"