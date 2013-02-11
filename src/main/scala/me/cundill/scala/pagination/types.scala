package me.cundill.scala.pagination

case class PaginationParams(pageNumber: Int, recordsPerPage: Int)

trait PaginatedDataRequest {
  val paginationParams: PaginationParams
  def firstRecordNumber = (paginationParams.pageNumber - 1) * paginationParams.recordsPerPage
}

trait PaginatedDataResponse[T] {
  val paginationParams: PaginationParams
  val totalRecordsAvailable: Int
  val totalPages: Int
  val records: List[T]
}

object Pagination {

  def calcTotalPages(totalRecordsAvailable: Int, recordsPerPage: Int): Int =
    Math.ceil(totalRecordsAvailable / recordsPerPage.asInstanceOf[Double]).asInstanceOf[Int]

}
