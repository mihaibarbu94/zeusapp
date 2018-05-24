package ic.zeus.mail_manager;

import android.os.AsyncTask;
import android.util.Log;

public class MailSender extends AsyncTask<String, Void, Void> {
    private Exception exception;

    protected Void doInBackground(String... urls) {
        try {
            MailComposer mailSender = new MailComposer();
            Log.e("Deb", "Here");
            String[] to = {"mmihai.barbu@gmail.com"};
            mailSender.setTo(to);
            //mailSender.addAttachment(file);
            mailSender.setBody(urls[0]);
            mailSender.send();
            Log.e("deb", "Sent Mail!");
        } catch (Exception e) {
            this.exception = e;
            Log.e("deb", "Sent Mail!" + e);

            return null;
        }

        return null;
    }

//    protected void onPostExecute(RSSFeed feed) {
//        // TODO: check this.exception
//        // TODO: do something with the feed
//    }
}
