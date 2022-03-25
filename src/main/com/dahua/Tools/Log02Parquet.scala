package com.dahua.Tools

import com.dahua.Utils.LogSchema.structType
import org.apache.spark.rdd.RDD
import com.dahua.Utils.NumFormat
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object Log02Parquet {
  def main(args: Array[String]): Unit = {
    if (args.length != 2) {
      println(
        """
          |      缺少参数！
          |inputpath   outputpath
          |""".stripMargin)
      sys.exit()
    }

    //创建SparkSession
    var conf = new SparkConf().set("spark.serializer","org.apache.spark.serializer.KryoSerializer")
    val spark = SparkSession.builder().config(conf).appName("Log02Parquet").master("local[1]").getOrCreate()
    var sc = spark.sparkContext

    //导入隐式转换
    import spark.implicits._

    //接收参数
    var Array(inputpath,outputpath) = args

    //读取数据
    val line : RDD[String] = sc.textFile(inputpath)

    //对数据进行ETL
    val logdata : RDD[Array[String]] = line.map(_.split(",", -1)).filter(_.length >= 85)

    //班清洗好的数据封装成Row对象
    val row: RDD[Row] = logdata.map( arr => {
      Row(
        arr(0),
        NumFormat.toInt(arr(1)),
        NumFormat.toInt(arr(2)),
        NumFormat.toInt(arr(3)),
        NumFormat.toInt(arr(4)),
        arr(5),
        arr(6),
        NumFormat.toInt(arr(7)),
        NumFormat.toInt(arr(8)),
        NumFormat.toDouble(arr(9)),
        NumFormat.toDouble(arr(10)),
        arr(11),
        arr(12),
        arr(13),
        arr(14),
        arr(15),
        arr(16),
        NumFormat.toInt(arr(17)),
        arr(18),
        arr(19),
        NumFormat.toInt(arr(20)),
        NumFormat.toInt(arr(21)),
        arr(22),
        arr(23),
        arr(24),
        arr(25),
        NumFormat.toInt(arr(26)),
        arr(27),
        NumFormat.toInt(arr(28)),
        arr(29),
        NumFormat.toInt(arr(30)),
        NumFormat.toInt(arr(31)),
        NumFormat.toInt(arr(32)),
        arr(33),
        NumFormat.toInt(arr(34)),
        NumFormat.toInt(arr(35)),
        NumFormat.toInt(arr(36)),
        arr(37),
        NumFormat.toInt(arr(38)),
        NumFormat.toInt(arr(39)),
        NumFormat.toDouble(arr(40)),
        NumFormat.toDouble(arr(41)),
        NumFormat.toInt(arr(42)),
        arr(43),
        NumFormat.toDouble(arr(44)),
        NumFormat.toDouble(arr(45)),
        arr(46),
        arr(47),
        arr(48),
        arr(49),
        arr(50),
        arr(51),
        arr(52),
        arr(53),
        arr(54),
        arr(55),
        arr(56),
        NumFormat.toInt(arr(57)),
        NumFormat.toDouble(arr(58)),
        NumFormat.toInt(arr(59)),
        NumFormat.toInt(arr(60)),
        arr(61),
        arr(62),
        arr(63),
        arr(64),
        arr(65),
        arr(66),
        arr(67),
        arr(68),
        arr(69),
        arr(70),
        arr(71),
        arr(72),
        NumFormat.toInt(arr(73)),
        NumFormat.toDouble(arr(74)),
        NumFormat.toDouble(arr(75)),
        NumFormat.toDouble(arr(76)),
        NumFormat.toDouble(arr(77)),
        NumFormat.toDouble(arr(78)),
        arr(79),
        arr(80),
        arr(81),
        arr(82),
        arr(83),
        NumFormat.toInt(arr(84))
      )
    })
    val df : DataFrame = spark.createDataFrame(row, structType)
    //df.show(10)

    //写出
    df.write.parquet(outputpath)

    //释放资源
    spark.stop()
    sc.stop()

  }













}
