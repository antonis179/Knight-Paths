package com.example.knight.features.knightmovement.usecases

import com.example.knight.features.knightmovement.models.BoardSize
import com.example.knight.features.knightmovement.models.Move
import com.example.knight.features.knightmovement.models.Node
import com.example.knight.features.knightmovement.models.Path
import com.example.knight.features.knightmovement.usecases.BacktrackingPathCalculationUsecase.PathResult.*
import com.example.knight.utils.Do
import timber.log.Timber
import java.util.*

interface PathCalculationUsecase {
    fun calculate(source: Node, destination: Node, size: BoardSize, maxMoves: Int): List<Path>
}

/*
 * Algorithm https://www.geeksforgeeks.org/the-knights-tour-problem-backtracking-1/
 */
class BacktrackingPathCalculationUsecase : PathCalculationUsecase {

    override fun calculate(source: Node, destination: Node, size: BoardSize, maxMoves: Int): List<Path> {
        val paths = mutableListOf<Path>()

        return Do.safe(
            {
                val result = findPaths(
                    source,
                    destination,
                    source.x,
                    source.y,
                    0,
                    maxMoves,
                    size,
                    mutableListOf(),
                    paths
                )
                return@safe if (result == NoResults) listOf() else paths
            }, {
                Timber.e(it)
                return@safe listOf()
            }
        )
    }

    private fun findPaths(
        source: Node,
        dest: Node,
        x: Int,
        y: Int,
        move: Int,
        maxMoves: Int,
        size: BoardSize,
        moves: MutableList<Move>,
        paths: MutableList<Path>
    ): PathResult {
        var nextX: Int
        var nextY: Int

        if (move <= maxMoves && (x == dest.x && y == dest.y)) return ResultsFound
        if (move < maxMoves) {
            for (i in 0 until 8) {
                nextX = x + xMoves[i]
                nextY = y + yMoves[i]

                if (inBounds(nextX, nextY, size) && !moves.containsNode(nextX, nextY)) {
                    moves.addMove(i, x, y, nextX, nextY)
                    when (findPaths(source, dest, nextX, nextY, move + 1, maxMoves, size, moves, paths)) {
                        ResultsFound -> {
                            //Found a path. Add it
                            paths.add(Path(moves.addSourceToMoves(source)))
                            moves.removeLast()
                        }
                        else -> {
                            //Backtrack by 1 move
                            moves.removeLast()
                        }
                    }
                }
            }
            return Calculating
        }

        return NoResults
    }

    private fun List<Move>.addSourceToMoves(source: Node): List<Move> {
        var sourceNode = source
        return map { move ->
            val nodes = mutableListOf(sourceNode).apply { addAll(move.nodes) }
            sourceNode = move.nodes.last()
            Move(nodes)
        }.toList()
    }

    /*
     * FIXME: Issue: this ignores direction, e.g. max 1 moves and x + 2, y -1 movement where order matters
     */
    private fun MutableList<Move>.addMove(
        i: Int,
        oldX: Int,
        oldY: Int,
        nextX: Int,
        nextY: Int
    ) {
        val nodes = mutableListOf<Node>()
        if (yMoves[i] == 1 || yMoves[i] == -1) {
            nodes.add(Node(nextY, oldX))
            nodes.add(Node(nextY, oldX + (xMoves[i] / 2)))
        } else {
            nodes.add(Node(oldY, nextX))
            nodes.add(Node(oldY + (yMoves[i] / 2), nextX))
        }
        nodes.add(Node(nextY, nextX))
        add(Move(nodes))
    }

    private fun inBounds(x: Int, y: Int, size: BoardSize) = x in 0 until size.width && y in 0 until size.height

    private fun List<Node>.contains(x: Int, y: Int) = contains(Node(y, x))

    private fun MutableList<Move>.containsNode(
        nextX: Int,
        nextY: Int
    ) = !filter { it.nodes.contains(nextX, nextY) }.isNullOrEmpty()

    companion object {
        //Valid moves - arrays must have the same size
        val xMoves = intArrayOf(2, 1, -1, -2, -2, -1,  1,  2)
        val yMoves = intArrayOf(1, 2,  2,  1, -1, -2, -2, -1)
    }

    private sealed interface PathResult {
        object NoResults : PathResult
        object ResultsFound : PathResult
        object Calculating : PathResult
    }

}