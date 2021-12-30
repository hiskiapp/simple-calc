package app.hiskia.pbwcalc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {
    EditText et_input1, et_input2;
    RadioGroup rg_operation;
    Button btn_count;
    TextView tv_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    private void initComponent() {
        et_input1 = findViewById(R.id.et_input1);
        et_input2 = findViewById(R.id.et_input2);
        rg_operation = findViewById(R.id.rg_operation);
        btn_count = findViewById(R.id.btn_count);
        tv_result = findViewById(R.id.tv_result);

        btn_count.setOnClickListener(view -> {
            calc(et_input1, et_input2, rg_operation, tv_result, view);
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void calc(EditText et_input1, EditText et_input2, RadioGroup rg_operation, TextView tv_result, View view)
    {
        if (et_input1.getText().toString().length() == 0) {
            et_input1.setError("Silahkan masukkan input 1");
        } else if (et_input2.getText().toString().length() == 0) {
            et_input2.setError("Silahkan masukkan input 2");
        } else {
            et_input1.setError(null);
            et_input2.setError(null);
            hideKeyboard(view);

            double input1 = Integer.parseInt(et_input1.getText().toString());
            double input2 = Integer.parseInt(et_input2.getText().toString());
            double result;

            switch (rg_operation.getCheckedRadioButtonId()) {
                case R.id.rb_add:
                    result = input1 + input2;
                    break;
                case R.id.rb_sub:
                    result = input1 - input2;
                    break;
                case R.id.rb_mlt:
                    result = input1 * input2;
                    break;
                case R.id.rb_div:
                    result = input1 / input2;
                    if (input2 == 0){
                        tv_result.setText('∞');
                        return;
                    }
                    break;
                default:
                    result = 0;
                    break;
            }
            tv_result.setText(Double.toString(round(result, 2)));
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}