package com.tdlschool.hometaskgame.injection

import com.tdlschool.hometaskgame.ui.GameViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [InjectionModule::class])
interface InjectionComponent {

    fun inject(target: GameViewModel)

}
