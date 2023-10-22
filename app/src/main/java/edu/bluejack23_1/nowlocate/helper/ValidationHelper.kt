package edu.bluejack23_1.nowlocate.helper

class ValidationHelper {

    companion object{
        fun isAlphaNumeric(str: String): Boolean {
            return str.matches(Regex("^(?=.*[A-Za-z])(?=.*\\d).+$"))
        }

        fun numOfWords(str: String): Int {
            return str.split(Regex("\\s+")).count()
        }

    }

}