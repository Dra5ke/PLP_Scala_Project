package application

import javafx.scene.chart.XYChart

class Render {
  // (LINE  (7 8) (12 12))
  // (CIRCLE (7 8) 5)
  // (RECTANGLE (12 14) (24 44))
  def getShape(command: String): XYChart.Series[Number, Number] = {
    var shape = command.filter(!_.isDigit).replaceAll("\\W", "")
    var numberString = command.replaceAll("[^\\d ]", "");


    shape match {
      case "LINE" => {
        val list = numberString.split("\\D+").filter(_.nonEmpty).toList.map(_.toInt)
        return drawLine(list(0), list(1), list(2), list(3))
      }
      case "CIRCLE" => {
        val list = numberString.split("\\D+").filter(_.nonEmpty).toList.map(_.toInt)
        return drawCircle(list(0), list(1), list(2))
      }
      case "RECTANGLE" => {
        val list = numberString.split("\\D+").filter(_.nonEmpty).toList.map(_.toInt)
        return drawRectangle(list(0), list(1), list(2), list(3))
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
          err -= dy; x += sx
        }
        if (e2 < dy) {
          err += dx; y += sy
        }
        res;
      }

      def hasNext = (sx * x <= sx * x1 && sy * y <= sy * y1)
    }

    val pixels = new XYChart.Series[Number, Number]

    for ((x, y) <- it) {
      println(x + "," + y)
      pixels.getData().add(new XYChart.Data(x, y))
    }

    return pixels
  }

  def drawCircle(x0: Int, y0: Int, radius: Int): XYChart.Series[Number, Number] = {
    val pixels = new XYChart.Series[Number, Number]
    var f=1-radius
    var ddF_x=1
    var ddF_y= -2*radius
    var x=0
    var y=radius

    pixels.getData().add(new XYChart.Data(x0, y0+radius))
    pixels.getData().add(new XYChart.Data(x0, y0-radius))
    pixels.getData().add(new XYChart.Data(x0+radius, y0))
    pixels.getData().add(new XYChart.Data(x0-radius, y0))

    while(x < y)
    {
      if(f >= 0)
      {
        y-=1
        ddF_y+=2
        f+=ddF_y
      }
      x+=1
      ddF_x+=2
      f+=ddF_x


      pixels.getData().add(new XYChart.Data(x0+x, y0+y))
      pixels.getData().add(new XYChart.Data(x0-x, y0+y))
      pixels.getData().add(new XYChart.Data(x0+x, y0-y))
      pixels.getData().add(new XYChart.Data(x0-x, y0-y))
      pixels.getData().add(new XYChart.Data(x0+y, y0+x))
      pixels.getData().add(new XYChart.Data(x0-y, y0+x))
      pixels.getData().add(new XYChart.Data(x0+y, y0-x))
      pixels.getData().add(new XYChart.Data(x0-y, y0-x))
    }

    return pixels
  }

  def drawRectangle(x0: Int, y0: Int, x1: Int, y1: Int): XYChart.Series[Number, Number] = {
    val pixels = new XYChart.Series[Number, Number]


    val verticalLeft = drawLine(x0,y0, x0, y1)
    verticalLeft.getData().forEach(p => {
      pixels.getData().add(p)
    })

    val verticalRight = drawLine(x1,y0, x1, y1)
    verticalRight.getData().forEach(p => {
      pixels.getData().add(p)
    })

    val horizontalTop = drawLine(x0,y1, x1, y1)
    horizontalTop.getData().forEach(p => {
      pixels.getData().add(p)
    })

    val horizontalBottom = drawLine(x0,y0, x1, y0)
    horizontalBottom.getData().forEach(p => {
      pixels.getData().add(p)
    })

    return pixels
  }
}
