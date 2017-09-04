package exercise.other

/**
  * Created by BG244210 on 31/08/2017.
  */
object TestZip {
  def main(args: Array[String]) {
    val list1: List[Int] =List(1,2,3,4,12)
    val list2: List[Int] =List(5,6,7,8,9)
    val list3: List[Int] =List(2,3,4,5,6)
    val list4: List[Int] =List(7,8,9,0,3)

    //拉链操作
    //1、zip函数将传进来的两个参数中相应位置上的元素组成一个pair数组。如果其中一个参数元素比较长，那么多余的参数会被删掉
    /*
    源码：
      def zip[A1 >: A, B, That](that: GenIterable[B])(implicit bf: CanBuildFrom[Repr, (A1, B), That]): That = {
    val b = bf(repr)
    val these = this.iterator
    val those = that.iterator
    while (these.hasNext && those.hasNext)
      b += ((these.next(), those.next()))
    b.result()
   }
     */
    val ends: List[(Int, Int)] =list1.zip(list2)
    println("元素相同，进行zip拉链操作结果："+ends)
    //元素相同，进行zip拉链操作结果：List((1,5), (2,6), (3,7), (4,8), (12,9))
    /*
      如果元素个数不对应
     */
    val moves: List[Int] =list2.take(3)
    println("元素不同，进行zip拉链操作结果："+list1.zip(moves))
    //元素不同，进行zip拉链操作结果：List((1,5), (2,6), (3,7))


    //2、zipAll 函数和zip函数类似，区别：如果其中一个元素个数比较少，那么将用默认的元素填充
    val ends2: List[(Any, Any)] =list1.zipAll(list2,list3,list4)
    println("元素相同，zipAll结果："+ends2)
    //元素相同，zipAll结果：List((1,5), (2,6), (3,7), (4,8), (12,9))
    /*
    如果元素个数不对应
     */
    val moves2: List[Int] =list3.take(2)
    println("元素不同，zipAll结果："+list1.zipAll(list2,moves2,list4))
    //元素不同，zipAll结果：List((1,5), (2,6), (3,7), (4,8), (12,9))


    //3、zipWithIndex函数可以将元素和其所在的下表组成一个pair
    val paris: Seq[Int] =Seq(1,34,2,34,5,34,1)
    val endsList: Seq[(Int, Int)] =paris.zipWithIndex
    println("zipWithIndex操作结果："+endsList)
    //zipWithIndex操作结果：List((1,0), (34,1), (2,2), (34,3), (5,4), (34,5), (1,6))


    //4、unzip函数拆分元祖为List
    val tuples=List((0,11),(1,12),(2,15),(3,17))
    val unzipEnd: (List[Int], List[Int]) =tuples.unzip
    println("unzip函数拆分后的结果--1："+unzipEnd._1)
    println("unzip函数拆分后的结果--2："+unzipEnd._2)
    /*
    unzip函数拆分后的结果--1：List(0, 1, 2, 3)
    unzip函数拆分后的结果--2：List(11, 12, 15, 17)
     */
  }
}
