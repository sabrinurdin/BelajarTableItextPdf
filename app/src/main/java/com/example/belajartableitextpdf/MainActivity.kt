package com.example.belajartableitextpdf

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.belajartableitextpdf.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    var listaUsuarios: ArrayList<User> = ArrayList()
    var listDataODP: ArrayList<DataODP> = ArrayList()


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isAceptado ->
        if (isAceptado) Toast.makeText(this, "IZIN DIBERIKAN", Toast.LENGTH_SHORT).show()
        else Toast.makeText(this, "IZIN DITOLAK", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*listaUsuarios.add(User("Laki-laki", "Sabri Nurdin", "abo@gmail.com"))
        listaUsuarios.add(User("Perempuan", "Ulfa Nurul Fatihah", "ulfa@gmail.com"))
        listaUsuarios.add(User("Laki-laki", "Nur Aqmal Nurdin", "aqmal07@gmail.com"))*/

        listDataODP.add(DataODP(INDEX = "1", DATEL = "PAREPARE", WITEL = "SULSELBAR", NAMA_ODP = "ODP-PIN-FA/01", USED = "6", RUSAK = "1", CAPACITY = "2", OCCUPANCY = "8.75"))
        listDataODP.add(DataODP(INDEX = "2", DATEL = "PAREPARE", WITEL = "SULSELBAR", NAMA_ODP = "ODP-PIN-FA/01", USED = "6", RUSAK = "1", CAPACITY = "2", OCCUPANCY = "8.75"))
        listDataODP.add(DataODP(INDEX = "3", DATEL = "PAREPARE", WITEL = "SULSELBAR", NAMA_ODP = "ODP-PIN-FA/01", USED = "6", RUSAK = "1", CAPACITY = "2", OCCUPANCY = "8.75"))

        binding.btnCrearPdf.setOnClickListener {
            verificarPermisos(it)
        }

    }

    private fun verificarPermisos(view: View) {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(this, "IZIN YANG DIBERIKAN", Toast.LENGTH_SHORT).show()
                crearPDF()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> {
                Snackbar.make(
                    view,
                    "IZIN INI DIPERLUKAN UNTUK MEMBUAT FILE",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("OK") {
                    requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }.show()
            }

            else -> {
                requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }

    }

    private fun crearPDF() {
        try {
            val carpeta = "/archivospdf"
            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + carpeta

            val dir = File(path)
            if (!dir.exists()) {
                dir.mkdirs()
                Toast.makeText(this, "FOLDER DIBUAT", Toast.LENGTH_SHORT).show()
            }

            val file = File(dir, "odp.pdf")
            val fileOutputStream = FileOutputStream(file)

            val documento = Document()
            PdfWriter.getInstance(documento, fileOutputStream)

            documento.open()

           /* val filename = "H:/App Android/BelajarTableItextPdf/app/src/main/res/drawable"
            val image = Image.getInstance(filename)
            documento.add(image)*/


            val titulo = Paragraph("Laporan ODP Telkom Akses \n\n", FontFactory.getFont("arial", 22f, Font.BOLD, BaseColor.BLACK))
            titulo.alignment = Element.ALIGN_CENTER
            documento.add(titulo)

            val tabla = PdfPTable(8)
            tabla.setWidths(floatArrayOf(0.7f, 2f, 0.9f,1f, 1.3f, 1.6f,1.7f, 1.7f))

            val cell = PdfPCell(Paragraph("NO",FontFactory.getFont("arial", 11f, Font.BOLD, BaseColor.WHITE)))
            cell.horizontalAlignment = Element.ALIGN_CENTER
            cell.verticalAlignment = Element.ALIGN_MIDDLE
            cell.backgroundColor = BaseColor.GRAY
            cell.setPadding(5F)

            val cell2 = PdfPCell(Paragraph("NAMA ODP",FontFactory.getFont("arial", 11f, Font.BOLD, BaseColor.WHITE)))
            cell2.horizontalAlignment = Element.ALIGN_CENTER
            cell2.verticalAlignment = Element.ALIGN_MIDDLE
            cell2.backgroundColor = BaseColor.GRAY

            val cell3 = PdfPCell(Paragraph("USED",FontFactory.getFont("arial", 11f, Font.BOLD, BaseColor.WHITE)))
            cell3.horizontalAlignment = Element.ALIGN_CENTER
            cell3.verticalAlignment = Element.ALIGN_MIDDLE
            cell3.backgroundColor = BaseColor.GRAY

            val cell4 = PdfPCell(Paragraph("RUSAK",FontFactory.getFont("arial", 11f, Font.BOLD, BaseColor.WHITE)))
            cell4.horizontalAlignment = Element.ALIGN_CENTER
            cell4.verticalAlignment = Element.ALIGN_MIDDLE
            cell4.backgroundColor = BaseColor.GRAY

            val cell5 = PdfPCell(Paragraph("CAPACITY",FontFactory.getFont("arial", 11f, Font.BOLD, BaseColor.WHITE)))
            cell5.horizontalAlignment = Element.ALIGN_CENTER
            cell5.verticalAlignment = Element.ALIGN_MIDDLE
            cell5.backgroundColor = BaseColor.GRAY

            val cell6 = PdfPCell(Paragraph("OCCUPANCY",FontFactory.getFont("arial", 11f, Font.BOLD, BaseColor.WHITE)))
            cell6.horizontalAlignment = Element.ALIGN_CENTER
            cell6.verticalAlignment = Element.ALIGN_MIDDLE
            cell6.backgroundColor = BaseColor.GRAY

            val cell7 = PdfPCell(Paragraph("DATEL",FontFactory.getFont("arial", 11f, Font.BOLD, BaseColor.WHITE)))
            cell7.horizontalAlignment = Element.ALIGN_CENTER
            cell7.verticalAlignment = Element.ALIGN_MIDDLE
            cell7.backgroundColor = BaseColor.GRAY

            val cell8 = PdfPCell(Paragraph("WITEL",FontFactory.getFont("arial", 11f, Font.BOLD, BaseColor.WHITE)))
            cell8.horizontalAlignment = Element.ALIGN_CENTER
            cell8.verticalAlignment = Element.ALIGN_MIDDLE
            cell8.backgroundColor = BaseColor.GRAY

            tabla.addCell(cell)
            tabla.addCell(cell2)
            tabla.addCell(cell3)
            tabla.addCell(cell4)
            tabla.addCell(cell5)
            tabla.addCell(cell6)
            tabla.addCell(cell7)
            tabla.addCell(cell8)


            for (i in listDataODP.indices) {
                val cells = PdfPCell(Paragraph(listDataODP[i].INDEX+".",FontFactory.getFont("arial", 11f, Font.BOLD, BaseColor.BLACK)))
                cells.horizontalAlignment = Element.ALIGN_CENTER
                cells.verticalAlignment = Element.ALIGN_MIDDLE
                cells.setPadding(3F)

                val cells1 = PdfPCell(Paragraph(listDataODP[i].NAMA_ODP,FontFactory.getFont("arial", 11f, Font.BOLD, BaseColor.BLACK)))
                cells1.horizontalAlignment = Element.ALIGN_CENTER
                cells1.verticalAlignment = Element.ALIGN_MIDDLE

                val cells2 = PdfPCell(Paragraph(listDataODP[i].USED,FontFactory.getFont("arial", 11f, Font.BOLD, BaseColor.BLACK)))
                cells2.horizontalAlignment = Element.ALIGN_CENTER
                cells2.verticalAlignment = Element.ALIGN_MIDDLE

                val cells3 = PdfPCell(Paragraph(listDataODP[i].RUSAK,FontFactory.getFont("arial", 11f, Font.BOLD, BaseColor.BLACK)))
                cells3.horizontalAlignment = Element.ALIGN_CENTER
                cells3.verticalAlignment = Element.ALIGN_MIDDLE

                val cells4 = PdfPCell(Paragraph(listDataODP[i].CAPACITY,FontFactory.getFont("arial", 11f, Font.BOLD, BaseColor.BLACK)))
                cells4.horizontalAlignment = Element.ALIGN_CENTER
                cells4.verticalAlignment = Element.ALIGN_MIDDLE

                val cells5 = PdfPCell(Paragraph(listDataODP[i].OCCUPANCY,FontFactory.getFont("arial", 11f, Font.BOLD, BaseColor.BLACK)))
                cells5.horizontalAlignment = Element.ALIGN_CENTER
                cells5.verticalAlignment = Element.ALIGN_MIDDLE

                val cells6 = PdfPCell(Paragraph(listDataODP[i].DATEL,FontFactory.getFont("arial", 11f, Font.BOLD, BaseColor.BLACK)))
                cells6.horizontalAlignment = Element.ALIGN_CENTER
                cells6.verticalAlignment = Element.ALIGN_MIDDLE

                val cells7 = PdfPCell(Paragraph(listDataODP[i].WITEL,FontFactory.getFont("arial", 11f, Font.BOLD, BaseColor.BLACK)))
                cells7.horizontalAlignment = Element.ALIGN_CENTER
                cells7.verticalAlignment = Element.ALIGN_MIDDLE


                tabla.addCell(cells)
                tabla.addCell(cells1)
                tabla.addCell(cells2)
                tabla.addCell(cells3)
                tabla.addCell(cells4)
                tabla.addCell(cells5)
                tabla.addCell(cells6)
                tabla.addCell(cells7)

            }

            tabla.widthPercentage = 100F
            documento.add(tabla)

            documento.close()



        } catch (e: FileNotFoundException) {
            e.printStackTrace();
        } catch (e: DocumentException) {
            e.printStackTrace()
        }
    }




}