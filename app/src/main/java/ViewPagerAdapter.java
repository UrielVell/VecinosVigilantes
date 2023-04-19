
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.vecinosvigilantes.AlertasFragment;
import com.example.vecinosvigilantes.GrupoFragment;
import com.example.vecinosvigilantes.PerfilFragment;


public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==0){
            return new PerfilFragment();
        }
        if (position==1){
            return new AlertasFragment();
        }
        if (position==3){
            return new GrupoFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
