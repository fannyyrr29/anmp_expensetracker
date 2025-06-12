import android.content.Context
import com.ubaya.anmp_expensetracker.model.ExpensesDatabase

val DB_NAME = "expensetracker"

fun buildDb(context:Context):ExpensesDatabase{
    val db = ExpensesDatabase.buildDatabase(context)
    return db
}