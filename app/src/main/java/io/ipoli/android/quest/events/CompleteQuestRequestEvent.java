package io.ipoli.android.quest.events;

import io.ipoli.android.quest.data.Quest;

/**
 * Created by Venelin Valkov <venelin@curiousily.com>
 * on 1/9/16.
 */
public class CompleteQuestRequestEvent {
    public final Quest quest;
    public final String source;



    public CompleteQuestRequestEvent(Quest quest, String source) {
        this.quest = quest;
        this.source = source;
    }
}
