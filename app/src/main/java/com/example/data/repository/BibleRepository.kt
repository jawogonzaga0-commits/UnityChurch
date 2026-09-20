package com.example.data.repository

import com.example.data.local.BibleDao
import com.example.data.local.BibleHighlightEntity
import com.example.data.model.BibleBook
import com.example.data.model.BibleHighlight
import com.example.data.model.DailyVerse
import com.example.data.model.Verse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BibleRepository(private val bibleDao: BibleDao) {

    val booksList: List<BibleBook> = listOf(
        BibleBook("GEN", "Genesis", "Old Testament", 6, "Law"),
        BibleBook("PSA", "Psalms", "Old Testament", 5, "Wisdom & Poetry"),
        BibleBook("PRO", "Proverbs", "Old Testament", 4, "Wisdom & Poetry"),
        BibleBook("ISA", "Isaiah", "Old Testament", 3, "Prophets"),
        BibleBook("MAT", "Matthew", "New Testament", 3, "Gospels"),
        BibleBook("JHN", "John", "New Testament", 4, "Gospels"),
        BibleBook("ROM", "Romans", "New Testament", 4, "Epistles"),
        BibleBook("1CO", "1 Corinthians", "New Testament", 3, "Epistles"),
        BibleBook("PHP", "Philippians", "New Testament", 4, "Epistles"),
        BibleBook("JAM", "James", "New Testament", 3, "Epistles"),
        BibleBook("REV", "Revelation", "New Testament", 3, "Prophecy")
    )

    private val versesDatabase: Map<String, Map<Int, List<String>>> = mapOf(
        "GEN" to mapOf(
            1 to listOf(
                "In the beginning God created the heaven and the earth.",
                "And the earth was without form, and void; and darkness was upon the face of the deep. And the Spirit of God moved upon the face of the waters.",
                "And God said, Let there be light: and there was light.",
                "And God saw the light, that it was good: and God divided the light from the darkness.",
                "And God called the light Day, and the darkness he called Night. And the evening and the morning were the first day.",
                "And God said, Let there be a firmament in the midst of the waters, and let it divide the waters from the waters.",
                "And God said, Let us make man in our image, after our likeness: and let them have dominion over the fish of the sea, and over the fowl of the air.",
                "So God created man in his own image, in the image of God created he him; male and female created he them.",
                "And God blessed them, and God said unto them, Be fruitful, and multiply, and replenish the earth.",
                "And God saw every thing that he had made, and, behold, it was very good. And the evening and the morning were the sixth day."
            ),
            2 to listOf(
                "Thus the heavens and the earth were finished, and all the host of them.",
                "And on the seventh day God ended his work which he had made; and he rested on the seventh day from all his work which he had made.",
                "And God blessed the seventh day, and sanctified it: because that in it he had rested from all his work which God created and made.",
                "And the Lord God formed man of the dust of the ground, and breathed into his nostrils the breath of life; and man became a living soul.",
                "And the Lord God planted a garden eastward in Eden; and there he put the man whom he had formed."
            ),
            3 to listOf(
                "Now the serpent was more subtil than any beast of the field which the Lord God had made.",
                "And the woman said unto the serpent, We may eat of the fruit of the trees of the garden:",
                "But of the fruit of the tree which is in the midst of the garden, God hath said, Ye shall not eat of it, neither shall ye touch it, lest ye die.",
                "And the Lord God called unto Adam, and said unto him, Where art thou?",
                "Therefore the Lord God sent him forth from the garden of Eden, to till the ground from whence he was taken."
            ),
            4 to listOf(
                "And Adam knew Eve his wife; and she conceived, and bare Cain, and said, I have gotten a man from the Lord.",
                "And she again bare his brother Abel. And Abel was a keeper of sheep, but Cain was a tiller of the ground.",
                "And in process of time it came to pass, that Cain brought of the fruit of the ground an offering unto the Lord."
            ),
            5 to listOf(
                "This is the book of the generations of Adam. In the day that God created man, in the likeness of God made he him;",
                "Male and female created he them; and blessed them, and called their name Adam, in the day when they were created.",
                "And Enoch walked with God: and he was not; for God took him."
            ),
            6 to listOf(
                "When men began to multiply on the surface of the ground, and daughters were born to them,",
                "God's sons saw that men's daughters were beautiful, and they took any that they wanted for themselves as wives.",
                "Yahweh said, \"My Spirit will not strive with man forever, because he also is flesh; so his days will be one hundred twenty years.\"",
                "The Nephilim were on the earth in those days, and also after that, when God's sons came in to men's daughters and had children with them. Those were the mighty men who were of old, men of renown.",
                "Yahweh saw that the wickedness of man was great on the earth, and that every imagination of the thoughts of his heart was only evil continually.",
                "Yahweh was sorry that he had made man on the earth, and it grieved him in his heart.",
                "Yahweh said, \"I will destroy man whom I have created from the surface of the ground—both man, and beast, and creeping things, and birds of the sky; for I am sorry that I have made them.\"",
                "But Noah found favor in the eyes of Yahweh."
            )
        ),
        "PSA" to mapOf(
            1 to listOf(
                "Blessed is the man that walketh not in the counsel of the ungodly, nor standeth in the way of sinners, nor sitteth in the seat of the scornful.",
                "But his delight is in the law of the Lord; and in his law doth he meditate day and night.",
                "And he shall be like a tree planted by the rivers of water, that bringeth forth his fruit in his season; his leaf also shall not wither; and whatsoever he doeth shall prosper.",
                "The ungodly are not so: but are like the chaff which the wind driveth away.",
                "For the Lord knoweth the way of the righteous: but the way of the ungodly shall perish."
            ),
            23 to listOf(
                "The Lord is my shepherd; I shall not want.",
                "He maketh me to lie down in green pastures: he leadeth me beside the still waters.",
                "He restoreth my soul: he leadeth me in the paths of righteousness for his name's sake.",
                "Yea, though I walk through the valley of the shadow of death, I will fear no evil: for thou art with me; thy rod and thy staff they comfort me.",
                "Thou preparest a table before me in the presence of mine enemies: thou anointest my head with oil; my cup runneth over.",
                "Surely goodness and mercy shall follow me all the days of my life: and I will dwell in the house of the Lord for ever."
            ),
            91 to listOf(
                "He that dwelleth in the secret place of the most High shall abide under the shadow of the Almighty.",
                "I will say of the Lord, He is my refuge and my fortress: my God; in him will I trust.",
                "Surely he shall deliver thee from the snare of the fowler, and from the noisome pestilence.",
                "He shall cover thee with his feathers, and under his wings shalt thou trust: his truth shall be thy shield and buckler.",
                "For he shall give his angels charge over thee, to keep thee in all thy ways."
            ),
            119 to listOf(
                "Blessed are the undefiled in the way, who walk in the law of the Lord.",
                "Thy word is a lamp unto my feet, and a light unto my path.",
                "I have sworn, and I will perform it, that I will keep thy righteous judgments.",
                "The entrance of thy words giveth light; it giveth understanding unto the simple.",
                "Great peace have they which love thy law: and nothing shall offend them."
            ),
            121 to listOf(
                "I will lift up mine eyes unto the hills, from whence cometh my help.",
                "My help cometh from the Lord, which made heaven and earth.",
                "He will not suffer thy foot to be moved: he that keepeth thee will not slumber.",
                "Behold, he that keepeth Israel shall neither slumber nor sleep.",
                "The Lord is thy keeper: the Lord is thy shade upon thy right hand.",
                "The Lord shall preserve thee from all evil: he shall preserve thy soul.",
                "The Lord shall preserve thy going out and thy coming in from this time forth, and even for evermore."
            )
        ),
        "PRO" to mapOf(
            3 to listOf(
                "My son, forget not my law; but let thine heart keep my commandments:",
                "For length of days, and long life, and peace, shall they add to thee.",
                "Let not mercy and truth forsake thee: bind them about thy neck; write them upon the table of thine heart:",
                "So shalt thou find favour and good understanding in the sight of God and man.",
                "Trust in the Lord with all thine heart; and lean not unto thine own understanding.",
                "In all thy ways acknowledge him, and he shall direct thy paths.",
                "Be not wise in thine own eyes: fear the Lord, and depart from evil."
            ),
            4 to listOf(
                "Hear, ye children, the instruction of a father, and attend to know understanding.",
                "Wisdom is the principal thing; therefore get wisdom: and with all thy getting get understanding.",
                "Keep thy heart with all diligence; for out of it are the issues of life.",
                "Put away from thee a froward mouth, and perverse lips put far from thee.",
                "Let thine eyes look right on, and let thine eyelids look straight before thee."
            )
        ),
        "ISA" to mapOf(
            40 to listOf(
                "Comfort ye, comfort ye my people, saith your God.",
                "The grass withereth, the flower fadeth: but the word of our God shall stand for ever.",
                "He shall feed his flock like a shepherd: he shall gather the lambs with his arm, and carry them in his bosom.",
                "Hast thou not known? hast thou not heard, that the everlasting God, the Lord, the Creator of the ends of the earth, fainteth not, neither is weary?",
                "He giveth power to the faint; and to them that have no might he increaseth strength.",
                "Even the youths shall faint and be weary, and the young men shall utterly fall:",
                "But they that wait upon the Lord shall renew their strength; they shall mount up with wings as eagles; they shall run, and not be weary; and they shall walk, and not faint."
            ),
            53 to listOf(
                "Who hath believed our report? and to whom is the arm of the Lord revealed?",
                "He is despised and rejected of men; a man of sorrows, and acquainted with grief.",
                "Surely he hath borne our griefs, and carried our sorrows: yet we did esteem him stricken, smitten of God, and afflicted.",
                "But he was wounded for our transgressions, he was bruised for our iniquities: the chastisement of our peace was upon him; and with his stripes we are healed.",
                "All we like sheep have gone astray; we have turned every one to his own way; and the Lord hath laid on him the iniquity of us all."
            )
        ),
        "MAT" to mapOf(
            5 to listOf(
                "And seeing the multitudes, he went up into a mountain: and when he was set, his disciples came unto him:",
                "And he opened his mouth, and taught them, saying,",
                "Blessed are the poor in spirit: for theirs is the kingdom of heaven.",
                "Blessed are they that mourn: for they shall be comforted.",
                "Blessed are the meek: for they shall inherit the earth.",
                "Blessed are they which do hunger and thirst after righteousness: for they shall be filled.",
                "Blessed are the merciful: for they shall obtain mercy.",
                "Blessed are the pure in heart: for they shall see God.",
                "Blessed are the peacemakers: for they shall be called the children of God.",
                "Ye are the light of the world. A city that is set on an hill cannot be hid.",
                "Let your light so shine before men, that they may see your good works, and glorify your Father which is in heaven."
            ),
            6 to listOf(
                "Take heed that ye do not your alms before men, to be seen of them: otherwise ye have no reward of your Father which is in heaven.",
                "After this manner therefore pray ye: Our Father which art in heaven, Hallowed be thy name.",
                "Thy kingdom come, Thy will be done in earth, as it is in heaven.",
                "Give us this day our daily bread.",
                "And forgive us our debts, as we forgive our debtors.",
                "And lead us not into temptation, but deliver us from evil: For thine is the kingdom, and the power, and the glory, for ever. Amen.",
                "Therefore take no thought, saying, What shall we eat? or, What shall we drink? or, Wherewithal shall we be clothed?",
                "But seek ye first the kingdom of God, and his righteousness; and all these things shall be added unto you."
            )
        ),
        "JHN" to mapOf(
            1 to listOf(
                "In the beginning was the Word, and the Word was with God, and the Word was God.",
                "The same was in the beginning with God.",
                "All things were made by him; and without him was not any thing made that was made.",
                "In him was life; and the life was the light of men.",
                "And the light shineth in darkness; and the darkness comprehended it not.",
                "And the Word was made flesh, and dwelt among us, (and we beheld his glory, the glory as of the only begotten of the Father,) full of grace and truth."
            ),
            3 to listOf(
                "There was a man of the Pharisees, named Nicodemus, a ruler of the Jews:",
                "Jesus answered and said unto him, Verily, verily, I say unto thee, Except a man be born again, he cannot see the kingdom of God.",
                "The wind bloweth where it listeth, and thou hearest the sound thereof, but canst not tell whence it cometh, and whither it goeth: so is every one that is born of the Spirit.",
                "For God so loved the world, that he gave his only begotten Son, that whosoever belieeth in him should not perish, but have everlasting life.",
                "For God sent not his Son into the world to condemn the world; but that the world through him might be saved."
            ),
            14 to listOf(
                "Let not your heart be troubled: ye believe in God, believe also in me.",
                "In my Father's house are many mansions: if it were not so, I would have told you. I go to prepare a place for you.",
                "Jesus saith unto him, I am the way, the truth, and the life: no man cometh unto the Father, but by me.",
                "Peace I leave with you, my peace I give unto you: not as the world giveth, give I unto you. Let not your heart be troubled, neither let it be afraid."
            ),
            15 to listOf(
                "I am the true vine, and my Father is the husbandman.",
                "Abide in me, and I in you. As the branch cannot bear fruit of itself, except it abide in the vine; no more can ye, except ye abide in me.",
                "I am the vine, ye are the branches: He that abideth in me, and I in him, the same bringeth forth much fruit: for without me ye can do nothing.",
                "Greater love hath no man than this, that a man lay down his life for his friends."
            )
        ),
        "ROM" to mapOf(
            8 to listOf(
                "There is therefore now no condemnation to them which are in Christ Jesus, who walk not after the flesh, but after the Spirit.",
                "For the law of the Spirit of life in Christ Jesus hath made me free from the law of sin and death.",
                "And we know that all things work together for good to them that love God, to them who are the called according to his purpose.",
                "What shall we then say to these things? If God be for us, who can be against us?",
                "Nay, in all these things we are more than conquerors through him that loved us.",
                "For I am persuaded, that neither death, nor life, nor angels, nor principalities, nor powers, nor things present, nor things to come,",
                "Nor height, nor depth, nor any other creature, shall be able to separate us from the love of God, which is in Christ Jesus our Lord."
            ),
            12 to listOf(
                "I beseech you therefore, brethren, by the mercies of God, that ye present your bodies a living sacrifice, holy, acceptable unto God, which is your reasonable service.",
                "And be not conformed to this world: but be ye transformed by the renewing of your mind, that ye may prove what is that good, and acceptable, and perfect, will of God.",
                "Be kindly affectioned one to another with brotherly love; in honour preferring one another;",
                "Rejoicing in hope; patient in tribulation; continuing instant in prayer;",
                "Overcome evil with good."
            )
        ),
        "PHP" to mapOf(
            4 to listOf(
                "Rejoice in the Lord alway: and again I say, Rejoice.",
                "Let your moderation be known unto all men. The Lord is at hand.",
                "Be careful for nothing; but in every thing by prayer and supplication with thanksgiving let your requests be made known unto God.",
                "And the peace of God, which passeth all understanding, shall keep your hearts and minds through Christ Jesus.",
                "Finally, brethren, whatsoever things are true, whatsoever things are honest, whatsoever things are just, whatsoever things are pure, whatsoever things are lovely, whatsoever things are of good report; if there be any virtue, and if there be any praise, think on these things.",
                "I can do all things through Christ which strengtheneth me.",
                "But my God shall supply all your need according to his riches in glory by Christ Jesus."
            )
        ),
        "JAM" to mapOf(
            1 to listOf(
                "My brethren, count it all joy when ye fall into divers temptations;",
                "Knowing this, that the trying of your faith worketh patience.",
                "If any of you lack wisdom, let him ask of God, that giveth to all men liberally, and upbraideth not; and it shall be given him.",
                "Every good gift and every perfect gift is from above, and cometh down from the Father of lights, with whom is no variableness, neither shadow of turning.",
                "Wherefore, my beloved brethren, let every man be swift to hear, slow to speak, slow to wrath:",
                "But be ye doers of the word, and not hearers only, deceiving your own selves."
            )
        ),
        "REV" to mapOf(
            21 to listOf(
                "And I saw a new heaven and a new earth: for the first heaven and the first earth were passed away; and there was no more sea.",
                "And I John saw the holy city, new Jerusalem, coming down from God out of heaven, prepared as a bride adorned for her husband.",
                "And God shall wipe away all tears from their eyes; and there shall be no more death, neither sorrow, nor crying, neither shall there be any more pain: for the former things are passed away.",
                "And he that sat upon the throne said, Behold, I make all things new. And he said unto me, Write: for these words are true and faithful.",
                "And he said unto me, It is done. I am Alpha and Omega, the beginning and the end. I will give unto him that is athirst of the fountain of the water of life freely."
            )
        )
    )

    fun getVerses(bookId: String, chapter: Int): List<Verse> {
        val book = booksList.find { it.id == bookId } ?: booksList.first()
        val chapterVerses = versesDatabase[bookId]?.get(chapter)
            ?: versesDatabase["PSA"]?.get(23)
            ?: listOf("Thy word is a lamp unto my feet, and a light unto my path.")

        return chapterVerses.mapIndexed { index, text ->
            Verse(
                bookId = book.id,
                bookName = book.name,
                chapter = chapter,
                verseNumber = index + 1,
                text = text
            )
        }
    }

    fun getAllHighlights(): Flow<List<BibleHighlight>> {
        return bibleDao.getAllHighlights().map { list ->
            list.map { entity ->
                BibleHighlight(
                    id = entity.id,
                    bookId = entity.bookId,
                    bookName = entity.bookName,
                    chapter = entity.chapter,
                    verseNumber = entity.verseNumber,
                    textSnippet = entity.textSnippet,
                    colorHex = entity.colorHex,
                    note = entity.note,
                    timestamp = entity.timestamp
                )
            }
        }
    }

    suspend fun saveHighlight(
        bookId: String,
        bookName: String,
        chapter: Int,
        verseNumber: Int,
        textSnippet: String,
        colorHex: String,
        note: String = ""
    ) {
        bibleDao.insertHighlight(
            BibleHighlightEntity(
                bookId = bookId,
                bookName = bookName,
                chapter = chapter,
                verseNumber = verseNumber,
                textSnippet = textSnippet,
                colorHex = colorHex,
                note = note
            )
        )
    }

    suspend fun removeHighlight(id: Long) {
        bibleDao.deleteHighlight(id)
    }

    suspend fun removeVerseHighlight(bookId: String, chapter: Int, verseNumber: Int) {
        bibleDao.removeVerseHighlight(bookId, chapter, verseNumber)
    }

    fun getDailyVerse(): DailyVerse {
        val dailyVerses = listOf(
            DailyVerse(
                reference = "Psalm 23:1-3",
                text = "The Lord is my shepherd; I shall not want. He maketh me to lie down in green pastures: he leadeth me beside the still waters. He restoreth my soul.",
                theme = "Divine Peace & Guidance",
                devotion = "In every season of life, Christ tenderly watches over our steps. Allow His quiet waters to replenish your spirit this morning.",
                date = "Today's Word"
            ),
            DailyVerse(
                reference = "Philippians 4:6-7",
                text = "Be careful for nothing; but in every thing by prayer and supplication with thanksgiving let your requests be made known unto God. And the peace of God shall keep your hearts.",
                theme = "Gratitude Over Worry",
                devotion = "Lay your burdens down before the throne of grace. Peace is not the absence of trouble, but the presence of the Lord.",
                date = "Today's Word"
            ),
            DailyVerse(
                reference = "Isaiah 40:31",
                text = "They that wait upon the Lord shall renew their strength; they shall mount up with wings as eagles; they shall run, and not be weary.",
                theme = "Renewed Strength",
                devotion = "When your energy wanes, God's endurance is limitless. Wait patiently upon His promises today.",
                date = "Today's Word"
            )
        )
        return dailyVerses[0]
    }
}
