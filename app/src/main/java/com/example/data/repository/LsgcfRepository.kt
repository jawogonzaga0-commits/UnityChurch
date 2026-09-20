package com.example.data.repository

import com.example.data.model.DiscipleshipLesson
import com.example.data.model.DoctrinePillar
import com.example.data.model.QuizQuestion
import com.example.data.model.SermonVideo
import com.example.data.model.StatementOfFaithArticle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LsgcfRepository {

    val sermons: List<SermonVideo> = listOf(
        SermonVideo(
            id = "sermon_01",
            title = "Walking in the Spirit",
            speaker = "Ptr. Emmanuel Santos",
            date = "Sep 14, 2026",
            series = "LIFE IN THE SPIRIT",
            duration = "48:22",
            scriptureRef = "Galatians 5:16-25",
            description = "Discover the life-transforming freedom of yielding daily to the Holy Spirit. Learn how to overcome the pull of the flesh through conscious communion and surrender.",
            videoGradientColors = listOf(0xFF2E1A10, 0xFF8A401A, 0xFFD4A345)
        ),
        SermonVideo(
            id = "sermon_02",
            title = "The Power of Prayer",
            speaker = "Ptr. Maria Reyes",
            date = "Sep 7, 2026",
            series = "PRAYER & FASTING",
            duration = "41:05",
            scriptureRef = "James 5:13-18",
            description = "The effective, fervent prayer of a righteous person accomplishes much. Pastor Maria breaks down relational prayer and how intercession shifts spiritual atmospheres.",
            videoGradientColors = listOf(0xFF1E2818, 0xFF4A6B38, 0xFFC4973B)
        ),
        SermonVideo(
            id = "sermon_03",
            title = "Grounded in Truth",
            speaker = "Ptr. Emmanuel Santos",
            date = "Aug 31, 2026",
            series = "DOCTRINE & FAITH",
            duration = "52:10",
            scriptureRef = "Ephesians 4:11-16",
            description = "Standing unwavering in a culture of confusion. An exposition on sound doctrine, spiritual maturity, and the unshakable rock of God's Word.",
            videoGradientColors = listOf(0xFF1C2028, 0xFF384A6B, 0xFFE0A942)
        ),
        SermonVideo(
            id = "sermon_04",
            title = "The Good Shepherd",
            speaker = "Ptr. David Chen",
            date = "Aug 24, 2026",
            series = "FAITH & COMFORT",
            duration = "44:18",
            scriptureRef = "Psalm 23 & John 10:11-15",
            description = "He leads us beside peaceful waters and restores our soul. A heart-stirring message on experiencing Christ's tenderness in seasons of trial.",
            videoGradientColors = listOf(0xFF261C2C, 0xFF5D396B, 0xFFC4973B)
        ),
        SermonVideo(
            id = "sermon_05",
            title = "To Live Is Christ",
            speaker = "Ptr. Emmanuel Santos",
            date = "Aug 17, 2026",
            series = "PHILIPPIANS",
            duration = "46:30",
            scriptureRef = "Philippians 1:12-26",
            description = "Paul's magnificent declaration from prison: 'For to me, to live is Christ, and to die is gain.' Finding purpose that transcendentally conquers circumstance.",
            videoGradientColors = listOf(0xFF2D2013, 0xFF704E24, 0xFFD4A345)
        )
    )

    val doctrines: List<DoctrinePillar> = listOf(
        DoctrinePillar(
            id = "doc_01",
            title = "The Holy Scriptures",
            subtitle = "Inerrancy, Authority & Sufficiency",
            summary = "We believe the 66 canonical books of the Old and New Testaments are the inspired, inerrant, and final authority in all matters of faith and life.",
            keyVerses = listOf("2 Timothy 3:16-17", "2 Peter 1:20-21", "Psalm 119:105"),
            explanation = "The Bible is God's self-revelation to humanity. Written by human authors under the divine superintendence of the Holy Spirit, it contains no error in its original manuscripts and serves as the believer's supreme guide for doctrine, reproof, correction, and training in righteousness."
        ),
        DoctrinePillar(
            id = "doc_02",
            title = "The Triune God",
            subtitle = "Father, Son & Holy Spirit",
            summary = "We believe in one sovereign, living, and true God, eternally existing in three co-equal, co-eternal persons.",
            keyVerses = listOf("Deuteronomy 6:4", "Matthew 28:19", "2 Corinthians 13:14"),
            explanation = "God is one in essence and three in persons: the Father, the Son, and the Holy Spirit. Each person possesses the same divine nature, attributes, and perfections, executing distinct yet harmonious roles in creation, providence, and redemption."
        ),
        DoctrinePillar(
            id = "doc_03",
            title = "The Lord Jesus Christ",
            subtitle = "True God & True Man",
            summary = "We believe in the virgin birth, sinless life, substitutionary atoning death, bodily resurrection, and imminent return of Jesus Christ.",
            keyVerses = listOf("John 1:1, 14", "Colossians 2:9", "1 Corinthians 15:3-4"),
            explanation = "Jesus Christ is the eternal Word made flesh. Fully God and fully human, He died on the cross as the perfect sacrifice for sinners, rose physically from the grave on the third day, ascended into heaven, and now intercedes as our High Priest."
        ),
        DoctrinePillar(
            id = "doc_04",
            title = "Salvation by Grace Alone",
            subtitle = "Sola Gratia, Sola Fide, Solus Christus",
            summary = "Salvation is the gift of God received exclusively through repentant faith in Jesus Christ, not by human deeds or religious merits.",
            keyVerses = listOf("Ephesians 2:8-9", "Romans 3:23-26", "Titus 3:5"),
            explanation = "Man is naturally dead in trespasses and sins and incapable of saving himself. Through the regenerating power of the Holy Spirit and the shed blood of Jesus, all who trust in Christ are justified, forgiven, adopted into God's family, and sealed with eternal life."
        ),
        DoctrinePillar(
            id = "doc_05",
            title = "The Ministry of the Holy Spirit",
            subtitle = "Helper, Comforter & Sanctifier",
            summary = "The Holy Spirit convicts the world of sin, regenerates the sinner, indwells the believer, and empowers the church for holiness and witness.",
            keyVerses = listOf("John 16:7-14", "Romans 8:9-16", "Acts 1:8"),
            explanation = "At the moment of conversion, every believer is baptized by the Holy Spirit into the Body of Christ. The Spirit bestows spiritual gifts for the edification of the congregation and produces divine fruit of love, joy, and peace in daily life."
        ),
        DoctrinePillar(
            id = "doc_06",
            title = "The Fellowship & Local Church",
            subtitle = "The Body of Christ on Earth",
            summary = "The church consists of all redeemed believers united in worship, fellowship, discipleship, and the Great Commission.",
            keyVerses = listOf("Matthew 16:18", "Acts 2:42-47", "Hebrews 10:24-25"),
            explanation = "The local church is the visible manifestation of Christ's Body. Members are called to gather weekly, celebrate the ordinances of Believer's Baptism and Communion, love one another, practice mutual accountability, and evangelize the nations."
        ),
        DoctrinePillar(
            id = "doc_07",
            title = "The Blessed Hope & Eternity",
            subtitle = "The Glorious Return of Christ",
            summary = "We look forward with joyful expectation to the physical, personal return of Jesus Christ, the resurrection of the dead, and the eternal reign of God.",
            keyVerses = listOf("Titus 2:13", "1 Thessalonians 4:16-17", "Revelation 21:1-4"),
            explanation = "History culminates in the triumphant return of Jesus Christ to judge the living and the dead. The redeemed will dwell with God in eternal joy in the new heaven and new earth, while the unrepentant will suffer conscious eternal separation."
        )
    )

    private val _lessons = MutableStateFlow(
        listOf(
            DiscipleshipLesson(
                number = 1,
                title = "Assurance of Salvation",
                memoryVerse = "1 John 5:11-12 - 'And this is the testimony: that God has given us eternal life, and this life is in His Son.'",
                scripturePassage = "1 John 5:1-13; John 10:27-30",
                summary = "True assurance rests not on subjective feelings, but on the unchangeable promise of God and the completed work of Jesus on the cross.",
                keyTakeaways = listOf(
                    "Salvation is based on God's factual promise, not shifting emotions.",
                    "He who has the Son has life; he who has not the Son does not have life.",
                    "Nothing can pluck the believer out of the Father's hand."
                ),
                reflectionQuestion = "How does knowing your salvation is secure in Christ affect your daily obedience and peace?",
                isCompleted = true
            ),
            DiscipleshipLesson(
                number = 2,
                title = "The Authority of God's Word",
                memoryVerse = "2 Timothy 3:16 - 'All Scripture is God-breathed and is useful for teaching, rebuking, correcting and training in righteousness.'",
                scripturePassage = "Psalm 119:9-16; 2 Peter 1:19-21",
                summary = "God has given His Word as a lamp to our feet. Daily reading, meditation, and obedience to Scripture are the foundation of discipleship.",
                keyTakeaways = listOf(
                    "Scripture is completely inspired by the Holy Spirit.",
                    "Daily feeding on the Word builds spiritual immunity against deception.",
                    "Meditation leads to practical obedience and fruitfulness."
                ),
                reflectionQuestion = "What practical habit can you commit to this week to read and meditate on Scripture daily?",
                isCompleted = true
            ),
            DiscipleshipLesson(
                number = 3,
                title = "The Privilege of Prayer",
                memoryVerse = "Philippians 4:6-7 - 'Do not be anxious about anything, but in every situation, by prayer and petition, with thanksgiving, present your requests to God.'",
                scripturePassage = "Matthew 6:5-15; Luke 11:1-13",
                summary = "Prayer is two-way intimate communion with our Heavenly Father. We learn the ACTS model: Adoration, Confession, Thanksgiving, and Supplication.",
                keyTakeaways = listOf(
                    "God invites us to boldly approach His throne of grace.",
                    "Prayer changes our perspective and aligns our desires with God's will.",
                    "Thanksgiving unlocks peace that surpasses human understanding."
                ),
                reflectionQuestion = "Which part of the ACTS model do you find most natural, and which do you want to cultivate more?",
                isCompleted = false
            ),
            DiscipleshipLesson(
                number = 4,
                title = "Walking in the Holy Spirit",
                memoryVerse = "Galatians 5:16 - 'So I say, walk by the Spirit, and you will not gratify the desires of the flesh.'",
                scripturePassage = "Galatians 5:16-26; Romans 8:1-14",
                summary = "The Christian life is impossible in our own strength. The indwelling Holy Spirit supplies the power to overcome sin and manifest Christlike character.",
                keyTakeaways = listOf(
                    "Surrendering daily to the Spirit neutralizes the power of the flesh.",
                    "The fruit of the Spirit is love, joy, peace, patience, kindness, goodness, faithfulness, gentleness, and self-control.",
                    "Keeping in step with the Spirit requires quiet attentiveness to His promptings."
                ),
                reflectionQuestion = "Where do you need the Spirit's self-control and patience most in your relationships today?",
                isCompleted = false
            ),
            DiscipleshipLesson(
                number = 5,
                title = "Overcoming Temptation",
                memoryVerse = "1 Corinthians 10:13 - 'No temptation has overtaken you except what is common to mankind. And God is faithful; he will not let you be tempted beyond what you can bear.'",
                scripturePassage = "Matthew 4:1-11; James 1:12-15",
                summary = "Temptation is not sin, but yielding to it is. We overcome by using the sword of the Spirit, fleeing compromised situations, and relying on God's promised escape route.",
                keyTakeaways = listOf(
                    "Jesus fought Satan's lures with specific written Scripture.",
                    "God always provides an exit route before temptation overtakes us.",
                    "Confessing temptations to trusted mentors weakens their grip."
                ),
                reflectionQuestion = "What specific Bible verse will you memorize to counter your primary area of vulnerability?",
                isCompleted = false
            ),
            DiscipleshipLesson(
                number = 6,
                title = "The Local Church & Fellowship",
                memoryVerse = "Hebrews 10:24-25 - 'And let us consider how we may spur one another on toward love and good deeds, not giving up meeting together...'",
                scripturePassage = "Acts 2:42-47; 1 Corinthians 12:12-27",
                summary = "No Christian is called to be an island. The local church is God's greenhouse for spiritual growth, mutual encouragement, and worship.",
                keyTakeaways = listOf(
                    "We are individual members of one unified body.",
                    "Fellowship involves vulnerability, mutual prayer, and bearing one another's burdens.",
                    "Consistent gathering prevents spiritual drifting and coldness of heart."
                ),
                reflectionQuestion = "How are you currently serving or connecting deeply within your LSGCF small group or ministry?",
                isCompleted = false
            ),
            DiscipleshipLesson(
                number = 7,
                title = "Biblical Stewardship & Giving",
                memoryVerse = "2 Corinthians 9:7 - 'Each of you should give what you have decided in your heart to give, not reluctantly or under compulsion, for God loves a cheerful giver.'",
                scripturePassage = "Malachi 3:10; 2 Corinthians 8:1-9",
                summary = "Everything we have belongs to God; we are stewards of His resources. Generous, cheerful giving honors God and expands His kingdom.",
                keyTakeaways = listOf(
                    "God owns all our time, talents, and treasure.",
                    "Giving is an act of heartfelt worship and faith.",
                    "Generosity breaks the idol of financial materialism."
                ),
                reflectionQuestion = "In what ways can you steward your resources more intentionally for God's glory?",
                isCompleted = false
            ),
            DiscipleshipLesson(
                number = 8,
                title = "Sharing Your Faith",
                memoryVerse = "Matthew 28:19 - 'Therefore go and make disciples of all nations, baptizing them in the name of the Father and of the Son and of the Holy Spirit.'",
                scripturePassage = "Romans 10:13-15; Acts 1:8",
                summary = "Every follower of Jesus is called to be an ambassador for Christ. Learn to share your personal testimony and the core gospel message with gentleness and clarity.",
                keyTakeaways = listOf(
                    "Your personal story of life before and after Christ is powerful.",
                    "The gospel is the power of God for salvation to everyone who believes.",
                    "We plant the seed; the Holy Spirit brings the conviction and harvest."
                ),
                reflectionQuestion = "Who is one person in your sphere of influence you can begin praying for by name this week?",
                isCompleted = false
            ),
            DiscipleshipLesson(
                number = 9,
                title = "Water Baptism & The Lord's Supper",
                memoryVerse = "Romans 6:4 - 'We were therefore buried with him through baptism into death in order that, just as Christ was raised from the dead... we too may live a new life.'",
                scripturePassage = "Matthew 28:19; 1 Corinthians 11:23-29",
                summary = "The two sacred ordinances given to the church by Christ: baptism as our public identification with Christ's death and resurrection, and communion as ongoing remembrance.",
                keyTakeaways = listOf(
                    "Baptism is an outward symbol of an inward spiritual reality.",
                    "The Lord's Supper causes us to examine our hearts and celebrate the Cross.",
                    "Both ordinances visibly unify the congregation in Christ."
                ),
                reflectionQuestion = "What does taking Communion mean to you personally each Sunday?",
                isCompleted = false
            ),
            DiscipleshipLesson(
                number = 10,
                title = "Christian Integrity & Holiness",
                memoryVerse = "1 Peter 1:15-16 - 'But just as he who called you is holy, so be holy in all you do; for it is written: Be holy, because I am holy.'",
                scripturePassage = "Colossians 3:1-17; Psalm 15:1-5",
                summary = "We are called to live distinct, holy lives in our workplace, school, home, and speech. Integrity means our private reality matches our public profession.",
                keyTakeaways = listOf(
                    "Holiness is being set apart for God's royal purposes.",
                    "Integrity builds trust and honors the name of Jesus before the watching world.",
                    "Grace enables righteous living rather than legalistic striving."
                ),
                reflectionQuestion = "Is there an area in your speech or digital habits that needs to align with holiness?",
                isCompleted = false
            ),
            DiscipleshipLesson(
                number = 11,
                title = "Discovering Spiritual Gifts",
                memoryVerse = "1 Peter 4:10 - 'Each of you should use whatever gift you have received to serve others, as faithful stewards of God’s grace in its various forms.'",
                scripturePassage = "Romans 12:3-8; 1 Corinthians 12:4-11",
                summary = "The Holy Spirit imparts distinct spiritual gifts to every believer for building up the church. Discovering and deploying your gifts brings profound fulfillment.",
                keyTakeaways = listOf(
                    "Every believer has at least one spiritual gift.",
                    "Gifts are intended for the edification of others, not personal pride.",
                    "Active service in the local church clarifies your specific calling."
                ),
                reflectionQuestion = "Which spiritual gifts (teaching, serving, mercy, encouragement, leadership) do you sense God developing in you?",
                isCompleted = false
            ),
            DiscipleshipLesson(
                number = 12,
                title = "Hope & The Eternal Destiny",
                memoryVerse = "Revelation 21:4 - 'He will wipe every tear from their eyes. There will be no more death or mourning or crying or pain, for the old order of things has passed away.'",
                scripturePassage = "1 Thessalonians 4:13-18; 2 Peter 3:10-14",
                summary = "Our ultimate home is not this broken world. Holding firmly to the promise of Christ's return, the bodily resurrection, and eternal fellowship with God inspires perseverance today.",
                keyTakeaways = listOf(
                    "Christ will return physically, visibly, and triumphantly.",
                    "Our present trials are not worthy to be compared with the glory that will be revealed.",
                    "Living with an eternal perspective shapes all daily choices."
                ),
                reflectionQuestion = "How does the promise of Christ's return bring you comfort in your present challenges?",
                isCompleted = false
            )
        )
    )
    val lessons: StateFlow<List<DiscipleshipLesson>> = _lessons.asStateFlow()

    fun toggleLessonCompleted(number: Int) {
        val current = _lessons.value.toMutableList()
        val index = current.indexOfFirst { it.number == number }
        if (index != -1) {
            val item = current[index]
            current[index] = item.copy(isCompleted = !item.isCompleted)
            _lessons.value = current
        }
    }

    val statementOfFaith: List<StatementOfFaithArticle> = listOf(
        StatementOfFaithArticle(
            articleNumber = "Article I",
            title = "The Scriptures",
            statement = "We believe that the Holy Bible, consisting of thirty-nine books of the Old Testament and twenty-seven books of the New Testament, is the plenary and verbally inspired Word of God. It is infallible and completely inerrant in the original manuscripts, and is the absolute and sufficient rule of faith and practice.",
            scripturalProofs = "2 Tim. 3:16-17; 2 Pet. 1:19-21; Psa. 119:89, 160"
        ),
        StatementOfFaithArticle(
            articleNumber = "Article II",
            title = "The True God",
            statement = "We believe that there is only one true and living God, an infinite, sovereign Spirit, maker and supreme ruler of heaven and earth. In the unity of the Godhead, there are three persons: the Father, the Son, and the Holy Spirit, equal in every divine perfection and executing distinct but harmonious offices in the great work of redemption.",
            scripturalProofs = "Exod. 20:2-3; Deut. 6:4; 1 Cor. 8:6; 1 John 5:7; Matt. 28:19"
        ),
        StatementOfFaithArticle(
            articleNumber = "Article III",
            title = "The Person and Work of Jesus Christ",
            statement = "We believe that the Lord Jesus Christ, the eternal Son of God, became man without ceasing to be God, having been conceived by the Holy Spirit and born of the Virgin Mary. He lived a sinless life, died as our substitutionary atoning sacrifice on the cross, rose bodily from the dead, ascended to heaven, and lives to make intercession for His people.",
            scripturalProofs = "John 1:1-2, 14; Luke 1:35; Rom. 3:24-25; 1 Pet. 2:24; Acts 1:9-10; Heb. 7:25"
        ),
        StatementOfFaithArticle(
            articleNumber = "Article IV",
            title = "The Holy Spirit",
            statement = "We believe that the Holy Spirit is a divine person, co-equal with God the Father and God the Son. He convicts the world of sin, righteousness, and judgment; regenerates the unbeliever upon repentance; permanently indwells every true believer; baptizes them into the Body of Christ; and seals them unto the day of redemption.",
            scripturalProofs = "John 14:16-17; 16:8-11; 1 Cor. 12:12-14; Rom. 8:9; Eph. 1:13-14"
        ),
        StatementOfFaithArticle(
            articleNumber = "Article V",
            title = "The Total Depravity of Man",
            statement = "We believe that man was created in innocence under the law of his Maker, but by voluntary transgression fell from his sinless state. In consequence, all mankind are now sinners, totally depraved by nature and practice, alienated from God, and under just condemnation to eternal ruin without defense or excuse.",
            scripturalProofs = "Gen. 1:26-27; 3:1-6; Rom. 3:10-19, 23; 5:12; Eph. 2:1-3"
        ),
        StatementOfFaithArticle(
            articleNumber = "Article VI",
            title = "Salvation by Grace",
            statement = "We believe that the salvation of sinners is wholly of grace through the mediatorial offices of the Son of God. Sinners are justified and forgiven solely through personal faith in the shed blood of Jesus Christ, completely apart from human works, sacramental merit, or human righteousness.",
            scripturalProofs = "Eph. 2:8-9; Rom. 3:24; 5:1; Titus 3:5; John 3:16; Acts 16:31"
        ),
        StatementOfFaithArticle(
            articleNumber = "Article VII",
            title = "The Local Church & Its Ordinances",
            statement = "We believe that a local church is a congregation of immersed believers associated by covenant in the faith and fellowship of the gospel, observing the ordinances of Christ, governed by His laws, and exercising the gifts invested in them by His Word. Its scriptural offices are pastors and deacons.",
            scripturalProofs = "Acts 2:41-42; 14:23; 1 Cor. 11:23-26; Matt. 28:19-20; 1 Tim. 3:1-13"
        ),
        StatementOfFaithArticle(
            articleNumber = "Article VIII",
            title = "The Second Coming & Eternity",
            statement = "We believe in the personal, bodily, and imminent return of our Lord Jesus Christ for His redeemed saints. We believe in the bodily resurrection of the saved unto life everlasting with God, and the bodily resurrection of the unsaved unto conscious, everlasting punishment in hell.",
            scripturalProofs = "1 Thess. 4:13-18; 1 Cor. 15:51-58; Rev. 20:11-15; 21:1-8; Matt. 25:46"
        )
    )

    val quizQuestions: List<QuizQuestion> = listOf(
        QuizQuestion(
            id = 1,
            question = "In Philippians 1:21, the Apostle Paul declares: 'For to me, to live is _____ and to die is gain'?",
            options = listOf("Faith", "Christ", "Peace", "Love"),
            correctIndex = 1,
            explanation = "Paul writes in Philippians 1:21: 'For to me, to live is Christ, and to die is gain.' Christ was the center and purpose of his entire life.",
            reference = "Philippians 1:21"
        ),
        QuizQuestion(
            id = 2,
            question = "In Genesis 6, who found favor in the eyes of Yahweh amidst the wickedness on the earth?",
            options = listOf("Abraham", "Noah", "Enoch", "Moses"),
            correctIndex = 1,
            explanation = "Genesis 6:8 records: 'But Noah found favor in the eyes of Yahweh.' He walked faithfully with God.",
            reference = "Genesis 6:8"
        ),
        QuizQuestion(
            id = 3,
            question = "What is the primary spiritual foundation for salvation according to Ephesians 2:8-9?",
            options = listOf(
                "Good works and charitable deeds",
                "By grace through faith, not of works",
                "Religious ceremonies and rituals",
                "Ancestral heritage and tradition"
            ),
            correctIndex = 1,
            explanation = "Ephesians 2:8-9 teaches: 'For by grace you have been saved through faith. And this is not your own doing; it is the gift of God, not a result of works, so that no one may boast.'",
            reference = "Ephesians 2:8-9"
        ),
        QuizQuestion(
            id = 4,
            question = "In Galatians 5:22-23, which virtue is named first among the fruit of the Holy Spirit?",
            options = listOf("Joy", "Peace", "Love", "Patience"),
            correctIndex = 2,
            explanation = "Galatians 5:22 states: 'But the fruit of the Spirit is love, joy, peace, patience, kindness, goodness, faithfulness, gentleness, self-control.'",
            reference = "Galatians 5:22-23"
        ),
        QuizQuestion(
            id = 5,
            question = "How many total books are collected within the inspired Christian Old and New Testament canon?",
            options = listOf("52", "66", "73", "40"),
            correctIndex = 1,
            explanation = "The biblical canon comprises 66 books: 39 books in the Old Testament and 27 books in the New Testament.",
            reference = "2 Timothy 3:16"
        )
    )
}
