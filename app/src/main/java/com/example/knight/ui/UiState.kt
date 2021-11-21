package com.example.knight.ui

sealed interface UiState

object LoadingState : UiState
object ContentState : UiState