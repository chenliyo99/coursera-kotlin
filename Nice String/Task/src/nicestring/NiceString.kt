package nicestring

fun String.isNice(): Boolean {
    var len = this.length
    if (len<2) return false
    var vowelNum = 0
    var twoContinuous = false
    var haveBuae = false
    if(this.get(0) in setOf<Char>('a','e','i','o','u')) vowelNum=1
    for(i in 1..len-1){
        var ch=this.get(i)
        if(ch in setOf<Char>('a','e','i','o','u')) vowelNum += 1
        if(!haveBuae&&(ch=='u'||ch=='a'||ch=='e') && (this.get(i-1) =='b'))
            haveBuae=true
        if(!twoContinuous&&ch==this.get(i-1)) twoContinuous=true
    }
    var condNum=0
    if(!haveBuae) condNum+=1
    if(twoContinuous) condNum+=1
    if(vowelNum>2) condNum+=1
    return condNum>1
}