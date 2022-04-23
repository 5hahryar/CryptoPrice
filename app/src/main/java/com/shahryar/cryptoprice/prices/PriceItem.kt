package com.shahryar.cryptoprice.prices

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shahryar.cryptoprice.R
import com.shahryar.cryptoprice.data.model.Currency
import com.shahryar.cryptoprice.data.model.getFakeCurrency

@Composable
fun PriceItem(item: com.shahryar.shared.data.model.Currency, onItemClick: (currency: com.shahryar.shared.data.model.Currency) -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(15.dp, 8.dp)
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
        backgroundColor = colorResource(id = R.color.item_price_card),
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
//                                    .background(color = Color.Red)
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
//                                            .background(color = Color.Cyan),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${
                                    String.format(
                                        "%.2f",
                                        item.percent_change_24h
                                    )
                                }%",
                                color = if (item.percent_change_24h >= 0) colorResource(
                                    id = R.color.green
                                ) else colorResource(
                                    id = R.color.red
                                ),
                                fontSize = 14.sp,
                                maxLines = 1,
//                                                modifier = Modifier.background(Color.Yellow)
                            )
                            Text(
                                text = "$${item.price}",
                                color = colorResource(id = R.color.text_color),
                                fontSize = 20.sp,
                                maxLines = 1,
//                                            modifier = Modifier.background(Color.Red)
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
                                text = "Market cap",
                                color = colorResource(id = R.color.text_alpha),
                                fontSize = 14.sp,
                                maxLines = 1
                            )
                            Text(
                                text = "$${item.market_cap}",
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
                                    text = "30 Day",
                                    color = colorResource(id = R.color.text_alpha),
                                    fontSize = 14.sp,
                                    maxLines = 1,
//                                                    modifier = Modifier.background(Color.Yellow)
                                )
                                Text(
                                    text = "${
                                        String.format(
                                            "%.2f",
                                            item.percent_change_30d
                                        )
                                    }%",
                                    color = if (item.percent_change_30d >= 0) colorResource(
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
                                    text = "7 Day",
                                    color = colorResource(id = R.color.text_alpha),
                                    fontSize = 14.sp,
                                    maxLines = 1,
//                                                    modifier = Modifier.background(Color.Yellow)
                                )
                                Text(
                                    text = "${
                                        String.format(
                                            "%.2f",
                                            item.percent_change_7d
                                        )
                                    }%",
                                    color = if (item.percent_change_7d >= 0) colorResource(
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