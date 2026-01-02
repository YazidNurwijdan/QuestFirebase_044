package com.example.pam14.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam14.modeldata.DetailSiswa
import com.example.pam14.modeldata.UIStateSiswa
import com.example.pam14.modeldata.toDataSiswa
import com.example.pam14.repositori.RepositorySiswa
import kotlinx.coroutines.launch

class EntryViewModel(
    private val repositorySiswa: RepositorySiswa
) : ViewModel() {

    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    /* Fungsi validasi input */
    private fun validasiInput(
        uiState: DetailSiswa = uiStateSiswa.detailSiswa
    ): Boolean {
        return with(uiState) {
            nama.isNotBlank() &&
                    alamat.isNotBlank() &&
                    telpon.isNotBlank()
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }

    /* Simpan data ke Firebase */
    fun addSiswa() {
        viewModelScope.launch {
            if (validasiInput()) {
                repositorySiswa.postDataSiswa(
                    uiStateSiswa.detailSiswa.toDataSiswa()
                )
            }
        }
    }
}