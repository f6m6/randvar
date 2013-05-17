object RandVar {
  class RV(d: List[(Double, Double)]) {
    import scala.util.Random
    val dist = d
    val support = d.map(_._2)
    val ps = d.map(_._1)
    val n = support.size
    
    def this(n: Int) = this((1 to n).map(x => (1.0/n, x.toDouble)).toList)
    
    def prod[S, T](a: List[S], b: List[T]): List[(S, T)] = a match {
      case (x::xs) => b.map((x, _)) ::: prod(xs, b)
      case _ => List()
    }
    def *(that: RV) = new RV(
      prod(this.dist, that.dist).map{ case ((p, u), (q, v)) => (p * q, u * v) }
    )
    
    def /(that: RV) = new RV(
      prod(this.dist, that.dist).map{ case ((p, u), (q, v)) => (p * q, u / v) }
    )
    
    def +(that: RV) = new RV(
      prod(this.dist, that.dist).map{ case ((p, u), (q, v)) => (p * q, u + v) }
    )
    
    def -(that: RV) = new RV(
      prod(this.dist, that.dist).map{ case ((p, u), (q, v)) => (p * q, u - v) }
    )
    
    def +(d: Double) = new RV(dist.map{ case (p, v) => (p, v + d) })
    def -(d: Double) = new RV(dist.map{ case (p, v) => (p, v - d) })
    def /(d: Double) = new RV(dist.map{ case (p, v) => (p, v / d) })
    def *(d: Double) = new RV(dist.map{ case (p, v) => (p, v * d) })
    
    def moment(r: Int) = dist.map{ case (p, k) => p * math.pow(k, r) }.sum
    def mean = moment(1)
    def variance = moment(2) - math.pow(moment(1), 2)
    def sd = math.sqrt(variance)

    def get = {
      def cumulative(k: Int) = ps.take(k).sum
      def in(x: Double, i: (Double, Double)) = i._1 < x && x <= i._2
      val sim = for (i <- 1 to n) yield (cumulative(i-1), cumulative(i))
      val r = Random.nextDouble
      val i = sim.map(in(r, _)).toList.indexOf(true)
      support(if (i == -1) 1 else i)
    }

    def unary_- = new RV(dist.map{ case (p, v) => (p, -v)})
    
    override def toString = dist.map{ 
      case (p, v) => "p(" + v + ") = " + p
    }.mkString(", ")
  }

  object RV {
    def apply(args: (Double, Double)*) = new RV(args.toList)
  }
}
