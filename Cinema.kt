package cinema

fun menu(): Int {
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
    return readLine()!!.toInt()
}

fun showSeats(rowsArray: Array<Array<String>>, top: String) {
    println()
    println("Cinema:")
    println(top)

    for (i in rowsArray.indices) {
        println("${i + 1} ${rowsArray[i].joinToString(" ")}")
    }
    println()
}

fun calculateTicketPrice(rows: Int, cols: Int, row: Int): Int =
        if (rows * cols <= 60) {
            10
        } else {
            if (row <= rows / 2) {
                10
            } else {
                8
            }
        }

fun buyTicket(rows: Int, cols: Int, rowsArray: Array<Array<String>>) {
    do {
        var err = false
        try {
            println("Enter a row number:")
            val row: Int = readLine()!!.toInt()
            println("Enter a seat number in that row:")
            val col: Int = readLine()!!.toInt()

            val ticketPrice: Int = calculateTicketPrice(rows, cols, row)

            if (rowsArray[row - 1][col - 1] == "B") {
                throw Exception("That ticket has already been purchased!")
            }

            println()
            println("Ticket price: $$ticketPrice")
            println()

            rowsArray[row - 1][col - 1] = "B"
        } catch (e: IndexOutOfBoundsException) {
            println("Wrong input!")
            err = true
        } catch (e: Exception) {
            println(e.message)
            err = true
        }
    } while(err)
}

fun showStatistics(rowsArray: Array<Array<String>>, cols: Int, rows: Int) {
    var soldTickets = 0
    var income = 0
    var totalIncome = 0
    for (a in rowsArray.indices) {
        for (t in rowsArray[a]) {
            if (t == "B") {
                soldTickets++
                income += calculateTicketPrice(rows, cols, a + 1)
            }
            totalIncome += calculateTicketPrice(rows, cols, a + 1)
        }
    }
    val seats = cols * rows
    val percentage = "%.2f".format(soldTickets.toDouble() / seats * 100) + "%"
    println("Number of purchased tickets: $soldTickets")
    println("Percentage: $percentage")
    println("Current income: $$income")
    println("Total income: $$totalIncome")
}

fun main() {
    println("Enter the number of rows:")
    val rows: Int = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val cols: Int = readLine()!!.toInt()

    var top = " "
    for (i in 1..cols) {
        top += " $i"
    }

    var rowsArray = emptyArray<Array<String>>()
    for (i in 0 until rows) {
        var colsArray = emptyArray<String>()

        for (j in 0 until cols) {
            colsArray += "S"
        }
        rowsArray += colsArray
    }

    do {
        val choice = menu()
        when (choice) {
            1 -> showSeats(rowsArray, top)
            2 -> buyTicket(rows, cols, rowsArray)
            3 -> showStatistics(rowsArray, cols, rows)
            0 -> return
        }
    } while (choice != 0)
}