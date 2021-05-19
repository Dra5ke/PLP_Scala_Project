package application

import javafx.scene.chart.XYChart
import javafx.scene.chart.XYChart.Series

class Render {
  // (LINE  (7 8) (12 12))
  // (CIRCLE (7 8) 5)
  // (RECTANGLE (12 14) (24 44))
  var boundingBox: List[Int] = List(0,1,2,3) //x0,y0,x1,y1

  def getShape(command: String): XYChart.Series[Number, Number] = {
    val shape = command.filter(!_.isDigit).replaceAll("\\W", "")
    val numberString = command.replaceAll("[^\\d ]", "");


    shape match {
      case "LINE" => {
        val list = numberString.split("\\D+").filter(_.nonEmpty).toList.map(_.toInt)
        return cleanBoundPixels(drawLine(list(0), list(1), list(2), list(3)))
      }
      case "CIRCLE" => {
        val list = numberString.split("\\D+").filter(_.nonEmpty).toList.map(_.toInt)
        return cleanBoundPixels(drawCircle(list(0), list(1), list(2)))
      }
      case "RECTANGLE" => {
        val list = numberString.split("\\D+").filter(_.nonEmpty).toList.map(_.toInt)
        return cleanBoundPixels(drawRectangle(list(0), list(1), list(2), list(3)))
      }
      case "BOUNDINGBOX" => {
         boundingBox = numberString.split("\\D+").filter(_.nonEmpty).toList.map(_.toInt)
        return drawRectangle(boundingBox(0), boundingBox(1), boundingBox(2), boundingBox(3))
      }
      case "BARCHART" => {
        val list = numberString.split("\\D+").filter(_.nonEmpty).toList.map(_.toInt)
        return cleanBoundPixels(drawBarChart(list))
      }
      case "PIECHART" => {
        val list = numberString.split("\\D+").filter(_.nonEmpty).toList.map(_.toInt)
        return cleanBoundPixels(drawPieChart(list))
      }
    }
  }


  def drawLine(x0: Int, y0: Int, x1: Int, y1: Int): XYChart.Series[Number, Number] = {
    val dx = x1 - x0
    val sx = if (x0 < x1) 1 else -1
    val dy = y1 - y0
    val sy = if (y0 < y1) 1 else -1

    def it = new Iterator[Tuple2[Double, Double]] {
      var x: Double = x0;
      var y: Double = y0
      var err = (if (dx > dy) dx else -dy) / 2

      def next = {
        val res = (x, y)
        val e2 = err;
        if (e2 > -dx) {
          err -= dy;
          x += sx
        }
        if (e2 < dy) {
          err += dx;
          y += sy
        }
        res;
      }

      def hasNext = (sx * x <= sx * x1 && sy * y <= sy * y1)
    }

    val pixels = new XYChart.Series[Number, Number]

    for ((x, y) <- it) {
      pixels.getData().add(new XYChart.Data(x, y))
    }

    return pixels
  }

  def drawCircle(x0: Int, y0: Int, radius: Int): XYChart.Series[Number, Number] = {
    val pixels = new XYChart.Series[Number, Number]
    var f = 1 - radius
    var ddF_x = 1
    var ddF_y = -2 * radius
    var x = 0
    var y = radius

    pixels.getData().add(new XYChart.Data(x0, y0 + radius))
    pixels.getData().add(new XYChart.Data(x0, y0 - radius))
    pixels.getData().add(new XYChart.Data(x0 + radius, y0))
    pixels.getData().add(new XYChart.Data(x0 - radius, y0))

    while (x < y) {
      if (f >= 0) {
        y -= 1
        ddF_y += 2
        f += ddF_y
      }
      x += 1
      ddF_x += 2
      f += ddF_x


      pixels.getData().add(new XYChart.Data(x0 + x, y0 + y))
      pixels.getData().add(new XYChart.Data(x0 - x, y0 + y))
      pixels.getData().add(new XYChart.Data(x0 + x, y0 - y))
      pixels.getData().add(new XYChart.Data(x0 - x, y0 - y))
      pixels.getData().add(new XYChart.Data(x0 + y, y0 + x))
      pixels.getData().add(new XYChart.Data(x0 - y, y0 + x))
      pixels.getData().add(new XYChart.Data(x0 + y, y0 - x))
      pixels.getData().add(new XYChart.Data(x0 - y, y0 - x))
    }

    return pixels
  }

  def drawRectangle(x0: Int, y0: Int, x1: Int, y1: Int): XYChart.Series[Number, Number] = {
    val pixels = new XYChart.Series[Number, Number]

    drawLine(x0, y0, x0, y1).getData().forEach(p => {
      pixels.getData().add(p)
    })

    drawLine(x1, y0, x1, y1).getData().forEach(p => {
      pixels.getData().add(p)
    })

    drawLine(x0, y1, x1, y1).getData().forEach(p => {
      pixels.getData().add(p)
    })

    drawLine(x0, y0, x1, y0).getData().forEach(p => {
      pixels.getData().add(p)
    })

    return pixels
  }

  def cleanBoundPixels(series: XYChart.Series[Number, Number]): XYChart.Series[Number, Number] = {

    var x0 = boundingBox(0)
    var y0 = boundingBox(1)
    var x1 = boundingBox(2)
    var y1 = boundingBox(3)

    // TRIED TO REMOVE THE POINTS OUT OF BOUNDING BOX BUT HAD PROBLEM WITH XYCHART REFS
    var cleanSeries = new Series[Number, Number]()

    series.getData().forEach(p => {
      var p_x = p.getXValue().doubleValue()
      var p_y = p.getYValue().doubleValue()

      if ((p_x > x0 && p_x < x1) && (p_y > y0 && p_y < y1)) {
        cleanSeries.getData().add(p)
      }
    })

    return cleanSeries
  }

  def drawBarChart(data : List[Int]): XYChart.Series[Number, Number] = {
    val pixels = new XYChart.Series[Number, Number]

    val boundingBoxHeight = boundingBox(3) -  boundingBox(1)
    //boundingBox() x0,y0,x1,y1
    val barHeight = boundingBoxHeight / data.length

    var tempRectangle = new XYChart.Series[Number, Number]

    var count:Int = 0


    for( x <- data ){
      println(count)
      println("x0: "+ boundingBox(0) + "y0: " + (boundingBox(1) + barHeight*count) + "x1: "+(boundingBox(0) + x)  + "y1: "+ (barHeight*(count+1)))
      tempRectangle = drawRectangle(boundingBox(0),boundingBox(1) + barHeight*count, boundingBox(0) + x, barHeight*(count+1))

      tempRectangle.getData().forEach(p => {
        pixels.getData().add(p)
      })
      count += 1
    }

    return pixels
  }

  def drawPieChart(data : List[Int]): XYChart.Series[Number, Number] = {
    val pixels = new XYChart.Series[Number, Number]
    var tempRectangle = new XYChart.Series[Number, Number]

    var width = boundingBox(2) - boundingBox(0)
    var height = boundingBox(3) - boundingBox(1)

    var shortSide : Int = 0

    if( width < height){
        shortSide = width
    } else{
      shortSide = height
    }

    var centerX = (boundingBox(0)+ boundingBox(2)/2)
    var centerY = (boundingBox(1)+ boundingBox(3)/2)

     drawCircle((boundingBox(0)+ boundingBox(2)/2),(boundingBox(1)+ boundingBox(3)/2), shortSide/2)

    var count:Int = 0

    for( x <- data ){
      println(count)

      tempRectangle.getData().forEach(p => {
        pixels.getData().add(p)
      })
      count += 1
    }


    tempRectangle.getData().forEach(p => {
      pixels.getData().add(p)
    })

    return pixels
  }
}
