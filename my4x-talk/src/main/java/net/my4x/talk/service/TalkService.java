package net.my4x.talk.service;

import net.my4x.talk.model.TalkStep;

public interface TalkService {
   TalkStep talk(String char1Id, String char2Id, String stepId);

}
