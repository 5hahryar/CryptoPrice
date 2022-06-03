package com.shahryar.cryptoprice.ui.prices

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shahryar.cryptoprice.R
import com.shahryar.shared.data.model.Currency
import com.shahryar.shared.data.model.CurrencyDto

@Composable
fun PriceItem(item: Currency, onItemClick: (currency: Currency) -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable(
                indication = rememberRipple(
                    bounded = true,
                    color = colorResource(id = R.color.ripple)
                ),
                onClick = { onItemClick(item) },
                enabled = true,
                interactionSource = MutableInteractionSource()
            ),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = androidx.compose.material.MaterialTheme.colors.primaryVariant,
        elevation = 0.dp
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(15.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        Column(verticalArrangement = Arrangement.aligned(Alignment.CenterVertically)) {
                            Text(
                                text = item.symbol,
                                color = colorResource(id = R.color.text_alpha),
                                fontSize = 14.sp,
                                maxLines = 1
                            )
                            Text(
                                text = item.name,
                                color = colorResource(id = R.color.text_color),
                                fontSize = 20.sp,
                                maxLines = 1
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${item.percentChange24h}%",
                                color = if (item.percentChange24h.toDouble() >= 0) colorResource(
                                    id = R.color.green
                                ) else colorResource(
                                    id = R.color.red
                                ),
                                fontSize = 14.sp,
                                maxLines = 1,
                            )
                            Text(
                                text = "$${item.price}",
                                color = colorResource(id = R.color.text_color),
                                fontSize = 20.sp,
                                maxLines = 1,
                            )
                        }
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight()
            )
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        Column(verticalArrangement = Arrangement.aligned(Alignment.CenterVertically)) {
                            Text(
                                text = stringResource(R.string.market_cap),
                                color = colorResource(id = R.color.text_alpha),
                                fontSize = 14.sp,
                                maxLines = 1
                            )
                            Text(
                                text = "$${item.marketCap}",
                                color = colorResource(id = R.color.text_color),
                                fontSize = 19.sp,
                                maxLines = 1
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
//                                            .background(color = Color.Cyan)
                    ) {
                        Row {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.day_30),
                                    color = colorResource(id = R.color.text_alpha),
                                    fontSize = 14.sp,
                                    maxLines = 1,
//                                                    modifier = Modifier.background(Color.Yellow)
                                )
                                Text(
                                    text = "${
                                        item.percentChange30d
                                    }%",
                                    color = if (item.percentChange30d.toDouble() >= 0) colorResource(
                                        id = R.color.green
                                    ) else colorResource(
                                        id = R.color.red
                                    ),
                                    fontSize = 19.sp,
                                    maxLines = 1,
//                                                    modifier = Modifier.background(Color.Red)
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.day_7),
                                    color = colorResource(id = R.color.text_alpha),
                                    fontSize = 14.sp,
                                    maxLines = 1,
//                                                    modifier = Modifier.background(Color.Yellow)
                                )
                                Text(
                                    text = "${
                                        item.percentChange7d
                                    }%",
                                    color = if (item.percentChange7d.toDouble() >= 0) colorResource(
                                        id = R.color.green
                                    ) else colorResource(
                                        id = R.color.red
                                    ),
                                    fontSize = 19.sp,
                                    maxLines = 1,
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
private fun Preview() {
    MaterialTheme {
//        PriceItem(item = getFakeCurrency(), onItemClick = {})
    }
}