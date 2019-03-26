package taxipark

import java.util.*

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> {
    //this.allDrivers.filter { this.trips. }
    var setDriver = this.allDrivers.toMutableSet()
    for(trip in this.trips){
        if(setDriver.contains(trip.driver))
            setDriver.remove(trip.driver)
    }
    return setDriver
}


/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> {
    var setPsg = HashSet<Passenger>()
    var mapPsg = HashMap<Passenger,Int>()
    for(psg in this.allPassengers)
        mapPsg.put(psg,0)
    for(trip in this.trips){
        for(psg in trip.passengers)
            mapPsg.put(psg,mapPsg.get(psg)!!.plus(1))
    }

    for(entry in mapPsg){
        if(entry.value>=minTrips) setPsg.add(entry.key)
    }
    return setPsg
}


/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> {
    var setPsg = HashSet<Passenger>()
    var mapPsg = HashMap<Passenger,Int>()
    for(psg in this.allPassengers)
        mapPsg.put(psg,0)
    for(trip in this.trips){
        if(trip.driver != driver) continue
        for(psg in trip.passengers)
            mapPsg.put(psg,mapPsg.get(psg)!!.plus(1))
    }
    for(entry in mapPsg){
        if(entry.value>=2) setPsg.add(entry.key)
    }
    return setPsg
}



/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> {
    var setPsg = HashSet<Passenger>()
    var mapPsg = HashMap<Passenger,Int>()
    var mapDisctPsg = HashMap<Passenger,Int>()
    for(psg in this.allPassengers){
        mapPsg.put(psg,0)
        mapDisctPsg.put(psg,0)
    }

    for(trip in this.trips){
        for(psg in trip.passengers){
            mapPsg.put(psg,mapPsg.get(psg)!!.plus(1))
            if(trip.discount!=null&&trip.discount<1.0)
                mapDisctPsg.put(psg,mapDisctPsg.get(psg)!!.plus(1))
        }

    }
    for(entry in mapPsg){
        var discNum = mapDisctPsg.get(entry.key)
        if(discNum!! > entry.value/2.0)
            setPsg.add(entry.key)
    }
    return setPsg
}



/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    var mapDuration = HashMap<Int,Int>()
    for(trip in this.trips) {
        var durSet = (trip.duration / 10).toInt()
        mapDuration.put(durSet, mapDuration.getOrDefault(durSet, 0)+1)
    }
    if(mapDuration.size ==0) return null
    var max = 0
    var cand = -1
    for(entry in mapDuration){
        if(entry.value>max) {
            max=entry.value
            cand=entry.key
        }
    }
    return cand*10..cand*10+9
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    var mapDriver = HashMap<Driver,Double>()

    for(dv in this.allDrivers)
        mapDriver.put(dv,0.0)
    for(trip in this.trips){
        mapDriver.put(trip.driver,
                mapDriver.getOrDefault(trip.driver,0.0)
                        +trip.cost)

    }
    var valList=mapDriver.values.toList().sortedDescending()
    var sum20 = 0.0
    var sumAll =0.0

    for(i in 0..valList.size-1){
        if(i+1<=valList.size*0.2) sum20+=valList.get(i)
        sumAll+=valList.get(i)
    }

    return (sum20>=sumAll*0.8)&&sum20>0.0;
}