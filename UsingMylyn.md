Use the following query pattern for Mylyn:

`<td.+?class="vt col_1".+?<a.+?>({Type}.+?)</a>.?</td>.?<td.+?class="vt col_2".+?<a.+?>({Status}.+?)</a>.?</td>.?<td.+?class="vt col_5".+?<a.+?>({Owner}.+?)</a>.?</td>.?<td.+?class="vt col_6".+?<a.+?href="detail\?id=({Id}[0-9]+).?">({Description}.+?)</a>.+?</td>`