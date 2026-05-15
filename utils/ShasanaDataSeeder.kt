package com.mindmatrix.nammashasane.utils

import com.mindmatrix.nammashasane.data.ShasanaDao
import com.mindmatrix.nammashasane.model.Shasana

object ShasanaDataSeeder {

    suspend fun seedDatabase(dao: ShasanaDao) {
        if (dao.getCount() > 0) return

        val shasanas = listOf(
            Shasana(
                title = "Shravanabelagola Inscription",
                dynasty = "Ganga Dynasty",
                period = "10th Century",
                location = "Shravanabelagola, Channarayapatna",
                latitude = 12.8551,
                longitude = 76.3943,
                translationKannada = "ಈ ಶಿಲಾಶಾಸನವು ರಾಜಾ ರಾಚಮಲ್ಲನ ಆಳ್ವಿಕೆಯಲ್ಲಿ ಗೊಮ್ಮಟೇಶ್ವರ ಮೂರ್ತಿಯ ಪ್ರತಿಷ್ಠಾಪನೆಯ ಬಗ್ಗೆ ದಾಖಲಿಸುತ್ತದೆ.",
                translationEnglish = "This stone inscription records the installation of the Gommatesvara statue during the reign of King Rachamalla. The statue was built by minister Chavundaraya.",
                king = "King Rachamalla",
                giftOrLaw = "Land grant and religious installation",
                imageUrl = ""
            ),
            Shasana(
                title = "Aihole Inscription",
                dynasty = "Chalukya Dynasty",
                period = "634 CE",
                location = "Aihole, Bagalkote District",
                latitude = 15.9630,
                longitude = 75.8748,
                translationKannada = "ಇದು ಕವಿ ರವಿಕೀರ್ತಿ ಬರೆದ ಪ್ರಸಿದ್ಧ ಶಾಸನ.",
                translationEnglish = "This famous inscription was written by poet Ravikirti. It mentions the great victories of Pulakesi II and the construction of the Meguti temple at Aihole.",
                king = "Pulakesi II",
                giftOrLaw = "Temple construction record",
                imageUrl = ""
            ),
            Shasana(
                title = "Manyakheta Inscription",
                dynasty = "Rashtrakuta Dynasty",
                period = "9th Century",
                location = "Manyakheta, Kalaburagi District",
                latitude = 17.4799,
                longitude = 76.9214,
                translationKannada = "ಈ ಶಾಸನವು ರಾಜಾ ಅಮೋಘವರ್ಷ I ರ ಕಾಲದ ಭೂಮಿ ದಾನದ ದಾಖಲೆ.",
                translationEnglish = "This inscription is a land grant record from the time of King Amoghavarsha I. It mentions several villages given to Brahmins.",
                king = "King Amoghavarsha I",
                giftOrLaw = "Agrahara grant - Brahmin village donation",
                imageUrl = ""
            ),
            Shasana(
                title = "Belur Chennakeshava Inscription",
                dynasty = "Hoysala Dynasty",
                period = "1117 CE",
                location = "Belur, Hassan District",
                latitude = 13.1648,
                longitude = 75.8644,
                translationKannada = "ಈ ಶಾಸನವು ರಾಜಾ ವಿಷ್ಣುವರ್ಧನನ ತಲಕಾಡು ಯುದ್ಧ ವಿಜಯ ಮತ್ತು ಚನ್ನಕೇಶವ ದೇವಸ್ಥಾನ ನಿರ್ಮಾಣ ದಾಖಲಿಸುತ್ತದೆ.",
                translationEnglish = "This inscription records King Vishnuvardhana's victory at Talakadu and the construction of the Chennakeshava temple.",
                king = "King Vishnuvardhana",
                giftOrLaw = "Temple construction and land grant",
                imageUrl = ""
            ),
            Shasana(
                title = "Hampi Vijayanagara Inscription",
                dynasty = "Vijayanagara Empire",
                period = "15th Century",
                location = "Hampi, Vijayanagara District",
                latitude = 15.3350,
                longitude = 76.4600,
                translationKannada = "ಈ ಶಾಸನವು ರಾಜಾ ದೇವರಾಯ II ರ ಆಳ್ವಿಕೆಯಲ್ಲಿ ವಿರೂಪಾಕ್ಷ ದೇವಸ್ಥಾನಕ್ಕೆ ನೀಡಿದ ದಾನ ದಾಖಲಿಸುತ್ತದೆ.",
                translationEnglish = "This inscription records donations to Virupaksha temple and trade regulations during the reign of King Devaraya II.",
                king = "King Devaraya II",
                giftOrLaw = "Temple donation and trade law",
                imageUrl = ""
            ),
            Shasana(
                title = "Banavasi Kadamba Inscription",
                dynasty = "Kadamba Dynasty",
                period = "5th Century",
                location = "Banavasi, Uttara Kannada",
                latitude = 14.5358,
                longitude = 75.0069,
                translationKannada = "ಕರ್ನಾಟಕದ ಮೊದಲ ಸ್ವತಂತ್ರ ರಾಜ್ಯ ಕದಂಬರ ಶಾಸನ.",
                translationEnglish = "Inscription of the Kadambas, Karnataka's first independent kingdom. Records the kingdom established by King Mayurasharma and temple donations.",
                king = "King Mayurasharma",
                giftOrLaw = "Kingdom founding and temple donation",
                imageUrl = ""
            ),
            Shasana(
                title = "Chikkamagaluru Rashtrakuta Inscription",
                dynasty = "Rashtrakuta Dynasty",
                period = "8th Century",
                location = "Chikkamagaluru",
                latitude = 13.3161,
                longitude = 75.7720,
                translationKannada = "ಈ ಶಾಸನವು ರಾಜ ದಂತಿದುರ್ಗನ ಆಳ್ವಿಕೆಯ ಮೊದಲ ದಾಖಲೆ.",
                translationEnglish = "This inscription is one of the earliest records of King Dantidurga's reign. It documents the founding of the Rashtrakuta empire.",
                king = "King Dantidurga",
                giftOrLaw = "Declaration of kingdom establishment",
                imageUrl = ""
            )
        )
        dao.insertAllShasanas(shasanas)
    }
}