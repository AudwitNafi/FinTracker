import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Expense(
    var id: String? = null,
    var userId: String? = null,
    var transactionType: String? = null,
    var expenseType: String? = null,
    var paymentMethod: String? = null,
    var date: String? = null,
    var note: String? = null,
    var amount: String? = null
) : Serializable {
    constructor() : this("", null, null, null, null, null, null, null)

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(userId)
        parcel.writeString(transactionType)
        parcel.writeString(expenseType)
        parcel.writeString(paymentMethod)
        parcel.writeString(date)
        parcel.writeString(note)
        parcel.writeString(amount)
    }

    fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Expense> {
        override fun createFromParcel(parcel: Parcel): Expense {
            return Expense(parcel)
        }

        override fun newArray(size: Int): Array<Expense?> {
            return arrayOfNulls(size)
        }
    }
}
