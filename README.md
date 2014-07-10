ConsoleSpawnmob
===============

Простой плагин для спауна мобов.

<b>Команды:</b>
<ul>
<li><code>/spawnmobx location world x y z count mob</code> - спаун моба в координатах <code>world x y z</code></li>
<li><code>/spawnmobx players world radius count mob</code> - спаун моба около игроков в мире <code>world</code></li>
</ul>

где <code>world</code> - имя мира, <code>x y z</code> - координаты, <code>count</code> - количество мобов,
<code>radius</code> - радиус спауна мобов вокруг игрока,
<code>mob</code> - имя сущности (например ZOMBIE, полный список взять можно тут - http://jd.bukkit.org/rb/apidocs/org/bukkit/entity/EntityType.html)

<b>Использование:</b>
<ul>
<li><code>/spawnmobx players world_the_end 5 10 ENDERMAN</code> - создаст в радиусе 5 блоков от всех игроков в Краю по 10 шт Эндерманов</li>
<li><code>/spawnmobx location world_nether -18 89 46 4 blaze</code> - создаст в Аду по координатам -18,89,46 4шт Blaze (имя сущности можно писать в любом регистре)</li>
</ul>
