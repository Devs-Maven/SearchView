package com.example.searchview

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ListViewModel @Inject constructor() : ViewModel() {

    val bookList = mutableStateListOf(
        "Think & Grow Rich",
        "Rich Dad Poor Dad",
        "The Power of Your Subconsious Mind",
        "Wings of Fire",
        "IKIGAI",
        "The Book Thief",
        "The Archer",
        "Atomic Habits",
        "The Psychology of Money",
        "Wuthering Heights",
        "The Book of Joy",
        "Common Stocks & Uncommon Profits",
        "Intelligent Investor",
        "Think like a Monk",
        "The Diary of a Young Girl",
        "The 5 AM Club",
        "The Richest Man in Babylon",
        "Murder on the Orient Express",
        "How to win friends & Influence People",
        "The Theory of Everything",
        "Clarity is the only Spirituality",
    )

    var searchList = mutableStateListOf<String>()


}