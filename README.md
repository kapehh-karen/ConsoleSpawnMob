ConsoleSpawnMob
===============

Простой плагин для спауна мобов.

<b>Команды:</b>
<ul>
<li><code>/spawnmobx location world x y z radius count mob</code> - спаун моба в координатах <code>world x y z</code></li>
<li><code>/spawnmobx players world radius count mob</code> - спаун моба около игроков в мире <code>world</code></li>
<li><code>/entities <i>[world]</i></code> - информация о всех сущностях в мире <code>world</code> (если аругмент опущен, то информация о всех мирах)</li>
</ul>

где <code>world</code> - имя мира, <code>x y z</code> - координаты, <code>count</code> - количество мобов,
<code>radius</code> - радиус спауна мобов,
<code>mob</code> - имя сущности (либо имя в секции конфига <i>mobstype</i>) (например ZOMBIE, полный список взять можно тут - http://jd.bukkit.org/rb/apidocs/org/bukkit/entity/EntityType.html)

<b>Использование:</b>
<ul>
<li><code>/spawnmobx players world_the_end 5 10 ENDERMAN</code> - создаст в радиусе 5 блоков от всех игроков в Краю по 10 шт Эндерманов</li>
<li><code>/spawnmobx location world_nether -18 89 46 5 4 blaze</code> - создаст в Аду по координатам -18,89,46 4шт Blaze (имя сущности, можно писать в любом регистре) в радиусе 5 блоков</li>
</ul>

<b>Конфиг:</b>
<pre>
# worldName - название мира, count - лимит живых существ не включая игроков
# (если 0, то безлимитно доступен спаун командой)
limit:
  world: 50 * players # вычисляемое выражение, где players переменная с количеством игроков
  world_nether: 0
  world_the_end: 0
  test: 0
limit_max_players: 100 # максимальное количество игроков на сервере (цифра не ограничивает их, нужна для вычислений)
tick_interval: 5 # интервал спауна мобов, лучше ставить 1
mobstype:
  MyZombie:
    entity: ZOMBIE # прототип моба
    name: Hulk # имя которое будет отображаться над головой
    damage: 12
    health: 100
    equip:
      weapon: DIAMOND_SWORD
      helmet: LEATHER_HELMET
      chestplate: LEATHER_CHESTPLATE
      leggings: LEATHER_CHESTPLATE
      boots: LEATHER_BOOTS
</pre>
