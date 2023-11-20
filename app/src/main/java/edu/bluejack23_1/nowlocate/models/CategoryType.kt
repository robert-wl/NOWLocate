package edu.bluejack23_1.nowlocate.models

enum class CategoryType {
    ELECTRONICS {
        override fun toString(): String {
            return "Electronics"
        }
    },
    PERSONAL_ITEMS {
        override fun toString(): String {
            return "Personal Items"
        }
    },
    KEYS {
        override fun toString(): String {
            return "Keys"
        }
    },
    WALLETS_AND_PURSES {
        override fun toString(): String {
            return "Wallets and Purses"
        }
    },
    DOCUMENTS {
        override fun toString(): String {
            return "Documents"
        }
    },
    JEWELRY {
        override fun toString(): String {
            return "Jewelry"
        }
    },
    BAGS_AND_LUGGAGE {
        override fun toString(): String {
            return "Bags and Luggage"
        }
    },
    BOOKS {
        override fun toString(): String {
            return "Books"
        }
    },
    GADGETS {
        override fun toString(): String {
            return "Gadgets"
        }
    },
    TOYS {
        override fun toString(): String {
            return "Toys"
        }
    };

    companion object {
        fun fromString(s: String): CategoryType? = values().find { it.toString() == s }
    }
}