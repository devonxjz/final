package com.example.util;

import javax.swing.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Utility class để chạy các thao tác Database trên Background Thread,
 * tránh block EDT (Event Dispatch Thread) gây đơ giao diện.
 *
 * Tích hợp cơ chế Lock UI: vô hiệu hóa component trigger trong lúc chạy
 * để ngăn người dùng spam click.
 */
public class SwingWorkerUtils {

    /**
     * Chạy task trả về kết quả trên background thread.
     *
     * @param triggerComponent  Component cần disable trong lúc chạy (có thể null)
     * @param backgroundTask    Supplier chạy trên background (gọi Service/DAO)
     * @param onSuccess         Consumer nhận kết quả, chạy trên EDT
     * @param onError           Consumer nhận Exception, chạy trên EDT
     */
    public static <T> void runAsync(
            JComponent triggerComponent,
            Supplier<T> backgroundTask,
            Consumer<T> onSuccess,
            Consumer<Exception> onError
    ) {
        if (triggerComponent != null) triggerComponent.setEnabled(false);

        SwingWorker<T, Void> worker = new SwingWorker<>() {
            @Override
            protected T doInBackground() throws Exception {
                return backgroundTask.get();
            }

            @Override
            protected void done() {
                if (triggerComponent != null) triggerComponent.setEnabled(true);
                try {
                    T result = get();
                    if (onSuccess != null) onSuccess.accept(result);
                } catch (Exception e) {
                    Throwable cause = e.getCause() != null ? e.getCause() : e;
                    if (onError != null) onError.accept((Exception) cause);
                }
            }
        };
        worker.execute();
    }

    /**
     * Chạy task không trả về kết quả (void) trên background thread.
     * Dùng cho thao tác Thêm/Sửa/Xóa.
     *
     * @param triggerComponent  Component cần disable trong lúc chạy (có thể null)
     * @param backgroundTask    Runnable chạy trên background
     * @param onSuccess         Runnable chạy trên EDT khi thành công
     * @param onError           Consumer nhận Exception, chạy trên EDT
     */
    public static void runAsyncVoid(
            JComponent triggerComponent,
            Runnable backgroundTask,
            Runnable onSuccess,
            Consumer<Exception> onError
    ) {
        if (triggerComponent != null) triggerComponent.setEnabled(false);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                backgroundTask.run();
                return null;
            }

            @Override
            protected void done() {
                if (triggerComponent != null) triggerComponent.setEnabled(true);
                try {
                    get(); // Check for exceptions
                    if (onSuccess != null) onSuccess.run();
                } catch (Exception e) {
                    Throwable cause = e.getCause() != null ? e.getCause() : e;
                    if (onError != null) onError.accept((Exception) cause);
                }
            }
        };
        worker.execute();
    }
}
