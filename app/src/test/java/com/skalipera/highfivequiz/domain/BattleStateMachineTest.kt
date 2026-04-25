package com.skalipera.highfivequiz.domain

import com.skalipera.highfivequiz.core.MatchPhase
import com.skalipera.highfivequiz.domain.battle.BattleEvent
import com.skalipera.highfivequiz.domain.battle.BattleStateMachine
import org.junit.Assert.assertEquals
import org.junit.Test

class BattleStateMachineTest {

    @Test
    fun `state machine follows expected battle progression`() {
        val machine = BattleStateMachine()

        machine.reduce(BattleEvent.SyncStarted)
        machine.reduce(BattleEvent.SyncCompleted)
        machine.reduce(BattleEvent.TriviaFinished)
        machine.reduce(BattleEvent.OpponentFinished)
        machine.reduce(BattleEvent.DragonSelected)
        machine.reduce(BattleEvent.ClashCompleted)
        machine.reduce(BattleEvent.RoundResolved)

        assertEquals(MatchPhase.MATCH_RESOLVE, machine.phase)
    }
}
