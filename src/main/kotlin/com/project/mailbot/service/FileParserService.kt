package com.project.mailbot.service

import com.project.mailbot.domain.EmailRecord
import com.project.mailbot.mail.EmailValidator
import org.apache.commons.csv.CSVFormat
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.apache.commons.csv.CSVParser
import org.apache.poi.ss.usermodel.WorkbookFactory

@Service
class FileParserService(val emailValidator: EmailValidator) {
    val FILETYPE_CSV = "text/csv"

    fun parseFile(file: MultipartFile): List<EmailRecord> {
        return if (FILETYPE_CSV == file.contentType) {
            val bufferedReader = file.inputStream.bufferedReader()
            val csvParser = CSVParser(bufferedReader, CSVFormat.DEFAULT)

            csvParser.records.map { it[0] as String }.filter { emailValidator.test(it) }
        } else  {
            val workbook = WorkbookFactory.create(file.inputStream)
            workbook.getSheetAt(0).map {
                it.map { cell->cell.stringCellValue }.filter { cell -> emailValidator.test(cell) }}.flatten()
        }.distinct().map { EmailRecord(it) }
    }
}