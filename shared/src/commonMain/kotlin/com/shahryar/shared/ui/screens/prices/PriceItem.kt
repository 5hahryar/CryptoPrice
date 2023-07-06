package com.shahryar.shared.ui.screens.prices

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shahryar.shared.SharedRes
import com.shahryar.shared.data.model.Currency
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun PriceExtendedCard(item: Currency, onItemClick: () -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable(
                indication = rememberRipple(
                    bounded = true,
                    color = Color.LightGray
                ),
                onClick = onItemClick,
                enabled = true,
                interactionSource = MutableInteractionSource()
            ),
        shape = RoundedCornerShape(20.dp),
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
                                color = colorResource(SharedRes.colors.text_alpha),
                                fontSize = 14.sp,
                                maxLines = 1
                            )
                            Text(
                                text = item.name,
                                color = colorResource(SharedRes.colors.text_color),
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
                                color = if (item.percentChange24h.toDouble() >= 0) colorResource(SharedRes.colors.green) else colorResource(SharedRes.colors.red),
                                fontSize = 14.sp,
                                maxLines = 1,
                            )
                            Text(
                                text = "$${item.price}",
                                color = colorResource(SharedRes.colors.text_color),
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
                                text = stringResource(SharedRes.strings.market_cap),
                                color = colorResource(SharedRes.colors.text_alpha),
                                fontSize = 14.sp,
                                maxLines = 1
                            )
                            Text(
                                text = "$${item.marketCap}",
                                color = colorResource(SharedRes.colors.text_color),
                                fontSize = 19.sp,
                                maxLines = 1
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        Row {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(SharedRes.strings.day_30),
                                    color = colorResource(SharedRes.colors.text_alpha),
                                    fontSize = 14.sp,
                                    maxLines = 1,
                                )
                                Text(
                                    text = "${
                                        item.percentChange30d
                                    }%",
                                    color = if (item.percentChange30d.toDouble() >= 0) colorResource(SharedRes.colors.green) else colorResource(SharedRes.colors.red),
                                    fontSize = 19.sp,
                                    maxLines = 1,
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(SharedRes.strings.day_7),
                                    color = colorResource(SharedRes.colors.text_alpha),
                                    fontSize = 14.sp,
                                    maxLines = 1,
                                )
                                Text(
                                    text = "${
                                        item.percentChange7d
                                    }%",
                                    color = if (item.percentChange7d.toDouble() >= 0) colorResource(SharedRes.colors.green) else colorResource(SharedRes.colors.red),
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