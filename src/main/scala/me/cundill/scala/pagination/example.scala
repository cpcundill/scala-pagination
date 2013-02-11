package me.cundill.scala.pagination

case class User(id: Int, name: String)

case class GetAllUsers(paginationParams: PaginationParams) extends PaginatedDataRequest

case class AllUsersSummary(paginationParams: PaginationParams,
                           totalRecordsAvailable: Int,
                           totalPages: Int,
                           records: List[User]) extends PaginatedDataResponse[User]

trait UserOperations {

  val totalUsers = 51

  def findAllUsers(request: GetAllUsers): AllUsersSummary = {
    val start = request.firstRecordNumber
    val end = start + request.paginationParams.recordsPerPage
    AllUsersSummary(paginationParams = request.paginationParams.copy(),
                    totalRecordsAvailable = totalUsers,
                    totalPages = Pagination.calcTotalPages(totalUsers, request.paginationParams.recordsPerPage),
                    records = generateUserList(request.firstRecordNumber, end))
  }

  private def generateUserList(start: Int, end: Int): List[User] =
    (start to (if (end > totalUsers) totalUsers else end))
      .map {i => User(i, s"User#$i")}.toList

}


object Example extends App with UserOperations {

  val recordsPerPage = 10

  val firstPageRequest = GetAllUsers(PaginationParams(1, recordsPerPage))
  println(findAllUsers(firstPageRequest))

  val secondPageRequest = GetAllUsers(PaginationParams(2, recordsPerPage))
  println(findAllUsers(secondPageRequest))

  val sixthPageRequest = GetAllUsers(PaginationParams(6, recordsPerPage))
  println(findAllUsers(sixthPageRequest))

}

