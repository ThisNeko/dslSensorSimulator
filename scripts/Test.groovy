duration 10

lawPolynomial "poly" value ([1.0d,2.0d,3.0d])
lawRandom "random"


zone "parking" nbSensor 5 using "poly"


sensor "try" using "poly" on "noName"
sensor "rand" using "random" on "noName"
sensor "test" using "poly" on "parking"

lot 3 using "random" on "parking"

//writeOn "csv"
writeOn "influxDB"
//writeOn "terminal"

go "Simulation"