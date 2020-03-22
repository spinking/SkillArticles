package ru.skillbranch.skillarticles.markdown

import java.util.regex.Pattern

object MarkdownParser {

    private val LINE_SEPARATOR =  System.getProperty("line.separator") ?: "\n"

    //group regex
    private const val UNORDERED_LIST_ITEM_GROUP = "(^[*+-] .+$)" // ненумерованный список имеет формать +-* пробел текст
    private const val HEADER_GROUP = "(^#{1,6} .+?$)" // заголовки, стартующие с 1 до 6 символов #
    private const val QUOTE_GROUP = "(^> .+?\$)" // цитирование начинается с символа >
    private const val ITALIC_GROUP = "((?<!\\*)\\*[^*].*?[^*]?\\*(?!\\*)|(?<!_)_[^_].*?[^_]?_(?!_))" // italic - окружение текста символами _ или *
    private const val BOLD_GROUP ="((?<!\\*)\\*{2}[^*].*?[^*]?\\*{2}(?!\\*)|(?<!_)_{2}[^_].*?[^_]?_{2}(?!_))" // bold - окружение текста символами __ или **
    private const val STRIKE_GROUP = "((?<!~)~{2}[^~].*?[^~]?~{2}(?!~))" //strike - окружение текста символами ~~
    private const val RULE_GROUP = "(^[-_*]{3}$)" // --- ___ *** - горизонтальный разделитель
    private const val INLINE_GROUP = "((?<!`)`[^`\\s].*?[^`\\s]?`(?!`))" //текст окруженный ` штрихом)
    private const val LINK_GROUP = "(\\[[^\\[\\]]*?]\\(.+?\\)|\\[*?]\\(.*?\\))"  // ссылка [title](url) [I`am yandex link](https://www.yandex.ru)
    private const val BLOCK_CODE_GROUP = "(^```[\\S\\s]+?```)"//"(^```[\\s\\S]+```$)"
    private const val ORDER_LIST_GROUP = "(^\\d{1,2}\\.\\s.+?$)"

    //result regex
    private const val MARKDOWN_GROUPS = "$UNORDERED_LIST_ITEM_GROUP|$HEADER_GROUP|$QUOTE_GROUP" +
            "|$ITALIC_GROUP|$BOLD_GROUP|$STRIKE_GROUP|$RULE_GROUP|$INLINE_GROUP|$LINK_GROUP|$BLOCK_CODE_GROUP|$ORDER_LIST_GROUP"

    private val elementsPattern by lazy { Pattern.compile(MARKDOWN_GROUPS, Pattern.MULTILINE) }

    /**
     * parse markdown text to elements
     */
    fun parse(string: String): MarkdownText { // Парсит элементы разметки
        val elements = mutableListOf<Element>()
        elements.addAll(findElements(string))
        return MarkdownText(elements)
    }

    /**
     * clear markdown text to string without markdown characters
     */
/*    fun clear(string: String?): String? {
        string ?: return null
        var clearedString = ""
        for(elm in findElements(string))
            clearedString += getSimple(elm)
        return clearedString
    }*/

/*    private fun getSimple(element: Element): String {
        var bufferString = ""
        for(e in element.elements)
            bufferString += getSimple(e)
        return if (element.elements.isEmpty()) element.text.toString() else bufferString
    }*/

    fun clear(string: String?): String? {
        string ?: return null
        var clearString = ""
        val matcher = elementsPattern.matcher(string)
        var lastStartIndex = 0

        loop@ while (matcher.find(lastStartIndex)) {
            val startIndex = matcher.start()
            val endIndex = matcher.end()
            // we found a mark
            if (lastStartIndex < startIndex) {
                // check what was before the mark
                clearString += string.substring(lastStartIndex, startIndex)
            }
            // check what kind of mark this was
            var text: String
            val groups = 1..11
            var group = -1
            for (gr in groups) {
                if (matcher.group(gr) != null) {
                    group = gr
                    break
                }
            }

            when (group) {
                //NOT FOUND -> BREAK
                -1 -> break@loop
                //1 -> LIST GROUP
                1 -> {
                    text = string.subSequence(startIndex.plus(2), endIndex).toString()
                    val subs = clear(text)
                    clearString += if (subs.isNullOrEmpty()) text else subs

                    lastStartIndex = endIndex
                }

                //2 -> HEADER GROUP
                2 -> {
                    val reg = "^#{1,6}".toRegex().find(string.substring(startIndex, endIndex))
                    val level = reg!!.value.length

                    clearString += string.substring(startIndex + level.inc(), endIndex)
                    lastStartIndex = endIndex
                }

                //3 -> QUOTE_GROUP
                3 -> {
                    text = string.substring(startIndex.plus(2), endIndex)
                    val subs = clear(text)
                    clearString += if (subs.isNullOrEmpty()) text else subs
                    lastStartIndex = endIndex
                }

                //4 -> ITALIC_GROUP
                4 -> {
                    text = string.substring(startIndex.inc(), endIndex.dec())
                    val subs = clear(text)
                    clearString += if (subs.isNullOrEmpty()) text else subs
                    lastStartIndex = endIndex
                }

                //5 -> BOLD_GROUP
                5 -> {
                    text = string.substring(startIndex.plus(2), endIndex.plus(-2))
                    val subs = clear(text)
                    clearString += if (subs.isNullOrEmpty()) text else subs
                    lastStartIndex = endIndex
                }

                //6 -> STRIKE_GROUP
                6 -> {
                    text = string.substring(startIndex.plus(2), endIndex.plus(-2))
                    val subs = clear(text)
                    clearString += if (subs.isNullOrEmpty()) text else subs
                    lastStartIndex = endIndex
                }

                //7 -> RULE_GROUP
                7 -> {
                    clearString += " "
                    lastStartIndex = endIndex
                }

                //8 -> INLINE CODE GROUP
                8 -> {
                    text = string.substring(startIndex.inc(), endIndex.dec())
                    clearString += text
                    lastStartIndex = endIndex
                }

                //9 -> LINK_GROUP
                9 -> {
                    text = string.substring(startIndex, endIndex)
                    val (title: String, _) = "\\[(.*)]\\((.*)\\)".toRegex().find(text)!!.destructured
                    clearString += title
                    lastStartIndex = endIndex
                }


                //10 -> BLOCK CODE
                10 -> {
                    text = string.subSequence(startIndex.plus(3), endIndex.plus(-3)).toString()
                    clearString += text
                    lastStartIndex = endIndex
                }

                //11 -> ORDERED LIST
                11 -> {
                    val reg = "(^\\d+)".toRegex().find(string.substring(startIndex, endIndex))
                    val order = reg!!.value
                    text = string.subSequence(startIndex, endIndex).toString()
                    val textWithoutOrder = string.subSequence(startIndex.plus(order.length), endIndex).toString()
                    val subs = clear(textWithoutOrder)
                    clearString += if (text.contains("(^\\d+)".toRegex())) text else subs

                    lastStartIndex = endIndex
                }
            }
        }

        // check if there's any more text left
        if (lastStartIndex < string.length) {
            clearString += string.substring(lastStartIndex, string.length)
        }

        return clearString
    }

    /**
     * find markdown elements in markdown text
     */
    private fun findElements(string: CharSequence): List<Element> {
        val parents = mutableListOf<Element>()
        val matcher = elementsPattern.matcher(string)
        var lastStartIndex = 0

        loop@while(matcher.find(lastStartIndex)) {
            val startIndex = matcher.start()
            val endIndex = matcher.end()

            // Если нашелся markdown маркер - то до него просто текст
            if(lastStartIndex < startIndex) {
                parents.add(Element.Text(string.subSequence(lastStartIndex, startIndex)))
            }

            var text: CharSequence
            val groups = 1..11 // Смотрим под какие типы markdown-а подходит
            var group = -1
            for(gr in groups) {
                if (matcher.group(gr) != null) {
                    group = gr
                    break
                }
            }
            when(group) {
                -1 -> break@loop // not found, break
                1 -> { // unordered list
                    // Смещаемся на 2 символа, так как ненумерованный список начинается +-* и пробела
                    text = string.subSequence(startIndex.plus(2), endIndex)

                    // Вложенные элементы (между первым и последним вхождением
                    val subs = findElements(text)
                    val element = Element.UnorderedListItem(text, subs)
                    parents.add(element)

                    // next find start from position "endIndex" (last regex character)
                    lastStartIndex = endIndex
                }
                2 -> { // header
                    val reg = "^#{1,6}".toRegex().find(string.subSequence(startIndex, endIndex))
                    val level = reg!!.value.length // уровень заголовка = кол-ву #
                    text = string.subSequence(startIndex.plus(level.inc()),endIndex) // Текст после ###
                    val element = Element.Header(level, text)
                    parents.add(element)
                    lastStartIndex = endIndex
                }
                3 -> { // quotes (цитаты)
                    text = string.subSequence(startIndex.plus(2),endIndex) // Текст после >
                    val subelements = findElements(text)

                    val element = Element.Quote(text, subelements) // Элемент из текста и подэлементы
                    parents.add(element)
                    lastStartIndex = endIndex
                }
                4 -> { // italic
                    text = string.subSequence(startIndex.inc(),endIndex.dec()) // Текст между _ или *
                    val subelements = findElements(text)

                    val element = Element.Italic(text, subelements) // Элемент из текста и подэлементы
                    parents.add(element)
                    lastStartIndex = endIndex
                }
                5 -> { // bold
                    text = string.subSequence(startIndex.plus(2),endIndex.plus(-2)) // Текст между __ или **
                    val subelements = findElements(text)

                    val element = Element.Bold(text, subelements) // Элемент из текста и подэлементы
                    parents.add(element)
                    lastStartIndex = endIndex
                }
                6 -> { // strike
                    text = string.subSequence(startIndex.plus(2),endIndex.plus(-2)) // Текст между ~~
                    val subelements = findElements(text)

                    val element = Element.Strike(text, subelements) // Элемент из текста и подэлементы
                    parents.add(element)
                    lastStartIndex = endIndex
                }
                7 -> { // rule
                    val element = Element.Rule() // Элемент из текста и подэлементы
                    parents.add(element)
                    lastStartIndex = endIndex
                }
                8 -> { // inline code
                    text = string.subSequence(startIndex.inc(),endIndex.dec()) // Текст между `

                    val element = Element.InlineCode(text)
                    parents.add(element)
                    lastStartIndex = endIndex
                }
                9 -> { // link
                    text = string.subSequence(startIndex,endIndex) // Текст между [title](link)
                    val (title:String, link: String) = "\\[(.*)]\\((.*)\\)".toRegex().find(text)!!.destructured
                    val element = Element.Link(link, title)
                    parents.add(element)
                    lastStartIndex = endIndex
                }
                //10 -> BLOCK CODE - optionally
                10 -> {
                    text = string.subSequence(startIndex.plus(3),endIndex.plus(-3)) // Текст между ```
                    if (text.contains(LINE_SEPARATOR)) {
                        for ((index, line) in text.lines().withIndex()) {
                            when (index) {
                                text.lines().lastIndex ->
                                    parents.add(Element.BlockCode(Element.BlockCode.Type.END, line))
                                0 ->
                                    parents.add(Element.BlockCode(Element.BlockCode.Type.START, line + LINE_SEPARATOR))
                                else ->
                                    parents.add(Element.BlockCode(Element.BlockCode.Type.MIDDLE, line + LINE_SEPARATOR))
                            }
                        }

                    } else parents.add(Element.BlockCode(Element.BlockCode.Type.SINGLE, text))
                    lastStartIndex = endIndex
                }

                //11 ->ORDER LIST
                11 -> {
                    val reg = "(^\\d{1,2}.)".toRegex().find(string.substring(startIndex, endIndex))
                    val order = reg!!.value
                    text = string.subSequence(startIndex.plus(order.length.inc()), endIndex).toString()
                    val subs = findElements(text)
                    parents.add(Element.OrderedListItem(order, text.toString(), subs))
                    lastStartIndex = endIndex
                }
            }
        }

        if(lastStartIndex<string.length) { // проверка после последнего вхождения, если там что то есть - то простой текст
            val text = string.subSequence(lastStartIndex, string.length)
            parents.add(Element.Text(text))
        }
        return parents
    }
}

// Обертка - список элементов
data class MarkdownText(val elements: List<Element>)

sealed class Element() { // Соответствует элементу markdown разметки
    abstract val text: CharSequence
    abstract val elements: List<Element> // дочерние подэлементы

    data class Text( // Элемент markdown - просто текст
        override val text: CharSequence,
        override val elements: List<Element> = emptyList()
    ) : Element()

    data class UnorderedListItem( //Элемент markdown - ненумерованный список
        override val text: CharSequence,
        override val elements: List<Element> = emptyList()
    ) : Element()

    data class Header(
        val level: Int = 1,
        override val text: CharSequence,
        override val elements: List<Element> = emptyList()
    ) : Element()

    data class Quote( // Цитаты
        override val text: CharSequence,
        override val elements: List<Element> = emptyList()
    ) : Element()

    data class Italic(
        override val text: CharSequence,
        override val elements: List<Element> = emptyList()
    ) : Element()

    data class Bold(
        override val text: CharSequence,
        override val elements: List<Element> = emptyList()
    ) : Element()

    data class Strike(
        override val text: CharSequence,
        override val elements: List<Element> = emptyList()
    ) : Element()

    data class Rule(
        override val text: CharSequence = " ", //for insert span (пустой элемент, чтобы прикрепить span)
        override val elements: List<Element> = emptyList()
    ) : Element()

    data class InlineCode(
        override val text: CharSequence, //for insert span
        override val elements: List<Element> = emptyList()
    ) : Element()

    data class Link(
        val link: String, // Собственно ссылка
        override val text: CharSequence, //for insert span
        override val elements: List<Element> = emptyList()
    ) : Element()

    data class OrderedListItem( // нумерованный список
        val order: String,
        override val text: CharSequence,
        override val elements: List<Element> = emptyList()
    ) : Element()

    data class BlockCode(
        val type: Type = Type.MIDDLE,
        override val text: CharSequence,
        override val elements: List<Element> = emptyList()
    ) : Element() {
        enum class Type { START, END, MIDDLE, SINGLE }
    }
}