package com.example.buscaminas.domain

fun modifyCell(
    cells: List<List<CellState>>,
    rowIndex: Int,
    colIndex: Int,
    newState: CellState
): List<List<CellState>> {
    if (cells[rowIndex][colIndex] == newState) return cells

    return cells.mapIndexed { r, row ->
        row.mapIndexed { c, cellState ->
            if (r == rowIndex && c == colIndex) newState else cellState
        }
    }
}

fun resetGame(
    rows: Int,
    columns: Int,
    numMines: Int
): List<List<CellState>> {
    val emptyBoard = MutableList(rows) { MutableList(columns) { CellState.Hidden(hasMine = false) } }
    var minesPlaced = 0

    while (minesPlaced < numMines) {
        val randomRow = (0 until rows).random()
        val randomCol = (0 until columns).random()

        if (!emptyBoard[randomRow][randomCol].isMine()) {
            emptyBoard[randomRow][randomCol] = CellState.Hidden(hasMine = true)
            minesPlaced++
        }
    }
    return emptyBoard.map { it.toList() }.toList()
}

fun revealCells(
    cells: List<List<CellState>>,
    rowIndex: Int,
    colIndex: Int
): List<List<CellState>> {
    val newCell: CellState = when (val cell = cells[rowIndex][colIndex]) {
        is CellState.Hidden -> {
            if (cell.hasMine) {
                CellState.MineExploded
            } else {
                CellState.Visible(countMines(cells, rowIndex, colIndex))
            }
        }
        else -> cell
    }

    var newCells = modifyCell(cells, rowIndex, colIndex, newCell)

    if (newCell is CellState.MineExploded) {
        newCells = newCells.mapIndexed { row, rowList ->
            rowList.mapIndexed { col, currentCell ->
                when {
                    row == rowIndex && col == colIndex -> CellState.MineExploded
                    currentCell is CellState.Hidden && currentCell.hasMine -> CellState.Mine
                    currentCell is CellState.Hidden -> CellState.Visible(
                        countMines(
                            cells,
                            row,
                            col
                        )
                    )
                    else -> currentCell
                }
            }
        }
    } else if (newCell is CellState.Visible && newCell.minesAround == 0) {
        val neighbours = findNeighboursIndex(cells, rowIndex, colIndex)
        neighbours.forEach { (row, col) ->
            if (cells[row][col] is CellState.Hidden) {
                newCells = revealCells(newCells, row, col)
            }
        }
    }

    return newCells
}

fun findNeighbours(
    cells: List<List<CellState>>,
    rowIndex: Int,
    colIndex: Int
): List<CellState> {
    val neighbours = findNeighboursIndex(cells, rowIndex, colIndex)
    return neighbours.map { (row, col) -> cells[row][col] }
}

fun findNeighboursIndex(
    cells: List<List<CellState>>,
    rowIndex: Int,
    colIndex: Int
): List<Pair<Int, Int>> {
    val neighbours = mutableListOf<Pair<Int, Int>>()
    val offsets = listOf(
        -1 to -1, -1 to 0, -1 to 1,
        0 to -1, 0 to 1,
        1 to -1, 1 to 0, 1 to 1
    )
    for (offset in offsets) {
        val row = rowIndex + offset.first
        val col = colIndex + offset.second

        cells.getOrNull(row)?.getOrNull(col)?.let { neighbour ->
            neighbours.add(row to col)
        }
    }
    return neighbours
}

fun countMines(
    cells: List<List<CellState>>,
    rowIndex: Int,
    colIndex: Int
): Int {
    val neighbours = findNeighbours(cells, rowIndex, colIndex)
    return neighbours.count { it.isMine() }
}

fun CellState.isMine() = this is CellState.Mine ||
        this is CellState.MineExploded ||
        this is CellState.Hidden && hasMine ||
        this is CellState.Flagged && hasMine