package net.my4x.talk.service;

import net.my4x.talk.model.TalkStep;

import org.springframework.stereotype.Service;

@Service
public class TalkServiceImpl implements TalkService {

   public TalkStep talk(String char1Id, String char2Id, String stepId) {
      // TODO Auto-generated method stub
      return new TalkStep();
   }

}
