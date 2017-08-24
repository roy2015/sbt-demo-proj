package exercise

import java.io.InputStream

/**
  * Created by BG244210 on 23/08/2017.
  */
object MainTest {
  /**
    * 测试数组
    */
  def test1 (): Unit = {
    val arry = Array(1,2,3)
    for (i <- arry) {
      println(i)
    }
  }

  /**
    * wordCount
    */
  def test2(): Unit = {
    //wordcount 1
    val list = List("guo", "jun", "roy" ,"guo", "jun", "jun")
    println( list.map( (_, 1)) )
    println( list.map( (_, 1)).groupBy(_._1) )
    println( list.map( (_, 1)).groupBy(_._1).mapValues( x => x.foldLeft(0)((a,b) => a + b._2) ) )
    val map = list.map( (_, 1)).groupBy(_._1).mapValues( x => x.foldLeft(0)((a,b) => a + b._2) )
    val listfin = map.toList
    println(listfin)
    println( listfin.sortBy(_._2) )
    println()

    //wordcount 2
    val list1 = List("guo roy jun", "roy guo1 jun1", "guo guo guo1 guo1 jun")
    println( list1.flatMap( _.split(" ")).map( (_, 1)).groupBy(_._1).mapValues( x => x.foldLeft(0)((a,b) => a + b._2)))
    println( list1.flatMap( _.split(" ")).map( (_, 1)).groupBy(_._1).mapValues( x => x.foldLeft(0)((a,b) => a + b._2)).toList.sortBy(_._2))
  }




  def main(args: Array[String]) {
//    test1();
//    test2()
    val  path = MainTest.getClass.getClassLoader.getResource("123.txt").getPath
    println(path)

  }
}
