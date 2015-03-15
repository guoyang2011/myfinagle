package cn.homework

/**
 * Created by yangguo on 15-3-14.
 */
object Test2 {
  type Vertex = Int
  type Graph = Map[Vertex, List[Vertex]]
  val g: Graph = Map(1 -> List(2, 4,5), 2 -> List(4, 3), 3 -> List(4, 5), 4 -> List(5, 3),5->List(4))
  //example graph meant to represent
  //  1---2
  //  |   |
  //  4---3
  val lookup = Map(
        "a1" -> List((7.0, "b2"), (9.0, "d4"), (14.0, "e5")),
        "b" -> List((10.0, "c3"), (15.0, "d4")),
        "c" -> List((11.0, "d"), (2.0, "e")),
        "d" -> List((6.0, "c"),(6.0,"e")),
        "e" -> List((9.0, "b"))
      )
  //I want this to return results in the different layers that it finds them (hence the list of list of vertex)
  def BFS(start: Vertex, g: Graph): List[List[Vertex]] = {
    val visited = List(start)
    val result = List(List(start))

    def BFS0(elems: List[Vertex], result: List[List[Vertex]], visited: List[Vertex]): List[List[Vertex]] = {
      val newNeighbors = elems.flatMap(g(_)).filterNot(visited.contains).distinct
      if (newNeighbors.isEmpty) result
      else BFS0(newNeighbors, newNeighbors :: result, visited ++ newNeighbors)
    }

    BFS0(List(start), result, visited).reverse
  }

  //I would really appreciate some input on DFS, I have the feeling there is a way to do this sans var.

  def DFS(start: Vertex, g: Graph): List[Vertex] = {
    var visited = List(start)
    var result = List(start)

    def DFS0(start: Vertex): Unit = {
      for (n <- g(start); if !visited.contains(n)) {
        visited = n :: visited
        result = n :: result
        DFS0(n)
      }
    }
    DFS0(start)
    result.reverse
  }

  def main(args: Array[String]) {
    println(BFS(1,g))
  }
}
