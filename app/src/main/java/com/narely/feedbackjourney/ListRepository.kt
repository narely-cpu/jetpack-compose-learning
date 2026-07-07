package com.narely.feedbackjourney

import com.narely.feedbackjourney.createuser.UserDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//class ListRepository {
//    private val _listRepository = MutableStateFlow<List<UserDataModel>>(emptyList())
//    val listRepository: StateFlow<List<UserDataModel>> = _listRepository
//
//    fun addNewItem(newItem: UserDataModel) {
//        _listRepository.value += newItem
//    }
//}

// 1. Retirar lógica do usersingleton e passar pras respectivas viewModel -> ok
// 2. Criar os usecases para obter/salvar/editar/remover lista
// 3. Criar um reporitory para chamar as 4 funcoes definidas nos usecases
// 4. Deixar o singleton privado (contendo apenas a lista) para que apenas o repository
// posso modificar o singleton