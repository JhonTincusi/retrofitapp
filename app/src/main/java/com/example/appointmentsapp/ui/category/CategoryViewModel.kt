import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appointmentsapp.io.ApiService
import com.example.appointmentsapp.io.response.Category
import com.example.appointmentsapp.io.response.CategoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    val categories = MutableLiveData<CategoryResponse>()

    fun fetchCategories() {
        val apiService = ApiService.create() // Asegúrate de que este método exista y esté configurado correctamente
        val prefs = getApplication<Application>().getSharedPreferences("UserLoginPrefs", Context.MODE_PRIVATE)
        val access = prefs.getString("access", null)
        val authToken = "Bearer ${access}"
        apiService.getCategories(authToken).enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                if (response.isSuccessful) {
                    // Actualiza el LiveData con la respuesta del servidor
                    categories.value = response.body()
                    Log.d("xdd",response.body().toString())
                } else {
                    // Maneja el caso de una respuesta no exitosa

                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                // Maneja el caso de fallo en la llamada
            }
        })
    }
}

