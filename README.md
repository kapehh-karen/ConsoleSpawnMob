ConsoleSpawnmob
===============

Простой плагин для спауна мобов.

<b>Команды:</b>
<ul>
<li><code>/spawnmobx location world x y z count mob</code> - спаун моба в координатах</li>
<li><code>/spawnmobx players patternName radius count mob</code> - спаун моба около игрока</li>
</ul>

где <code>world</code> - имя мира, <code>x y z</code> - координаты, <code>count</code> - количество мобов,
<code>radius</code> - радиус спауна мобов вокруг игрока,
<code>patternName</code> - регулярное выражение для выборки игроков (для всех игроков будет <code>.*</code>),
<code>mob</code> - имя сущности в верхнем регистре (например ZOMBIE, полный список взять можно тут - http://jd.bukkit.org/rb/apidocs/org/bukkit/entity/EntityType.html)
