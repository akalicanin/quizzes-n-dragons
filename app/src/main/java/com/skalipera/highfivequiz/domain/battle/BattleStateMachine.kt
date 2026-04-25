package com.skalipera.highfivequiz.domain.battle

import com.skalipera.highfivequiz.core.MatchPhase

class BattleStateMachine(initialPhase: MatchPhase = MatchPhase.SETUP) {

    var phase: MatchPhase = initialPhase
        private set

    fun reduce(event: BattleEvent): MatchPhase {
        phase = when (phase) {
            MatchPhase.SETUP -> if (event == BattleEvent.SyncStarted) MatchPhase.TAP_1_SYNC else phase
            MatchPhase.TAP_1_SYNC -> if (event == BattleEvent.SyncCompleted) MatchPhase.TRIVIA else phase
            MatchPhase.TRIVIA -> if (event == BattleEvent.TriviaFinished) MatchPhase.WAITING else phase
            MatchPhase.WAITING -> if (event == BattleEvent.OpponentFinished) MatchPhase.DRAGON_SELECT else phase
            MatchPhase.DRAGON_SELECT -> if (event == BattleEvent.DragonSelected) MatchPhase.TAP_2_CLASH else phase
            MatchPhase.TAP_2_CLASH -> if (event == BattleEvent.ClashCompleted) MatchPhase.ROUND_RESOLVE else phase
            MatchPhase.ROUND_RESOLVE -> if (event == BattleEvent.RoundResolved) MatchPhase.MATCH_RESOLVE else phase
            MatchPhase.MATCH_RESOLVE -> if (event == BattleEvent.NextRoundStarted) MatchPhase.SETUP else phase
        }
        return phase
    }
}

enum class BattleEvent {
    SyncStarted,
    SyncCompleted,
    TriviaFinished,
    OpponentFinished,
    DragonSelected,
    ClashCompleted,
    RoundResolved,
    NextRoundStarted
}
