package exercise

import java.io.{File, InputStream}

import exercise.other.{Delimeter, FrenchPunctuation, RichFile}

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

  //::  :::  ++
  def test3() :Unit = {
    val listA = scala.collection.immutable.List(1,2,3)
    val listB = scala.collection.immutable.List(4,5,6)
    println( listA.::(4) )
    println( listA ++ listB )
    println( listA ::: listB )

    /*for (i <- list1) {
      println(i)
    }*/
  }

  //隐式方法
  def test4() : Unit = {
    import RichFile.File2RichFile
    println( new File("123").read("456.txt") )
  }

  def test5() = {
    import FrenchPunctuation.delimeter
    println( FrenchPunctuation.quoto("Bonjour le monde") )
  }

  def test6[T] (a: T, b: T)(implicit order:T => Ordered[T]) ={

    if ( order(a) < b ) {
      val ret =a
      println(ret)
    } else {
      val ret =b
      println(ret)
    }
  }

  def main(args: Array[String]) {
//    test1()
//    test2()
//    test3()
//    test4()
//    test5()
    test6(11,2)


    /*val k:Int = 1
    println( k.until(7) )*/
  }

}
