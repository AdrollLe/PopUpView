package view.adroll.popupview.dialog.callback;

/**
 * Created by Adroll
 * on 2020/3/25
 * Description: 播放菜单上下移动栏目
 * * * * * * * * * * * * * * * * * * * * * *
 * Thinking is more important than coding. *
 * * * * * * * * * * * * * * * * * * * * * *
 */
public interface PopUpViewTransferCallBack {

    /**
     * 按上键
     * @param currentIndex：按上键所在的行数
     */
    void moveUp(int currentIndex);

    /**
     * 按下键
     * @param currentIndex：按下键所在的行数
     */
    void moveDown(int currentIndex);

    /**
     * 显示动画已结束，防止快速按上/下键导致动画执行过快，ui显示恶心
     */
    void animationOver();
}
