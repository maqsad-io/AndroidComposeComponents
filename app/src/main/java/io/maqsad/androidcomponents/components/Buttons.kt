package io.maqsad.androidcomponents.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.maqsad.androidcomponents.R
import io.maqsad.androidcomponents.extensions.customClickable
import io.maqsad.androidcomponents.ui.theme.ColorPrimary
import io.maqsad.androidcomponents.ui.theme.DarkGreyColor
import io.maqsad.androidcomponents.ui.theme.GreyColor

@Composable
fun MaqsadPrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(
        defaultElevation = 0.dp,
        pressedElevation = 2.dp,
        disabledElevation = 0.dp
    ),
    shape: Shape = RoundedCornerShape(8.dp),
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = ColorPrimary),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit = {
        PrimaryRegularText(
            text = text ?: "",//stringResource(id = R.string.continueText),
            fontSize = 14.sp
        )
    }
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        elevation = elevation,
        interactionSource = interactionSource,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun MaqsadIconButton(
    modifier: Modifier = Modifier,
    iconTint: Color = DarkGreyColor,
    onClick: () -> Unit,
    @DrawableRes icon: Int,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = iconTint
        )
    }
}

@Composable
fun MaqsadOutlineButton(
    modifier: Modifier = Modifier,
    defaultBackgroundColor: Color = Color.Transparent,
    pressedBackgroundColor: Color = ColorPrimary,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    OutlinedButton(
        modifier = modifier.height(44.dp),
        onClick = onClick,
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(backgroundColor = if (isPressed) pressedBackgroundColor else defaultBackgroundColor),
        border = BorderStroke(width = 1.dp, color = ColorPrimary),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp),
        content = content
    )
}

@Composable
fun IconTextButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    iconSize: Dp = 12.dp,
    drawablePadding: Dp = 0.dp,
    iconAlignment: Alignment.Vertical,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    if (iconAlignment == Alignment.Top) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(4.dp))
                .customClickable(onClick = onClick, noIndication = true),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(iconSize),
                painter = painterResource(id = icon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(drawablePadding))
            content()
        }
    } else if (iconAlignment == Alignment.Bottom) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(4.dp))
                .customClickable(onClick = onClick, noIndication = true),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            content()
            Spacer(modifier = Modifier.height(drawablePadding))
            Image(
                modifier = Modifier.size(iconSize),
                painter = painterResource(id = icon),
                contentDescription = null
            )
        }
    }
}

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    iconSize: Dp = 12.dp,
    drawablePadding: Dp = 0.dp,
    iconAlignment: Alignment.Vertical,
    content: @Composable () -> Unit
) {
    if (iconAlignment == Alignment.Top) {
        Column(
            modifier = modifier.clip(RoundedCornerShape(4.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(iconSize),
                painter = painterResource(id = icon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(drawablePadding))
            content()
        }
    } else if (iconAlignment == Alignment.Bottom) {
        Column(
            modifier = modifier.clip(RoundedCornerShape(4.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            content()
            Spacer(modifier = Modifier.height(drawablePadding))
            Image(
                modifier = Modifier.size(iconSize),
                painter = painterResource(id = icon),
                contentDescription = null
            )
        }
    }
}

@Composable
fun IconTextButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    iconSize: Dp = 12.dp,
    iconTint: Color = DarkGreyColor,
    drawablePadding: Dp = 0.dp,
    iconAlignment: Alignment.Horizontal,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    if (iconAlignment == Alignment.Start) {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(4.dp))
                .customClickable(onClick = onClick, bounded = true),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(start = 10.dp)
                    .size(iconSize),
                painter = painterResource(id = icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = iconTint)
            )
            Spacer(modifier = Modifier.width(drawablePadding))
            content()
        }
    } else if (iconAlignment == Alignment.End) {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(4.dp))
                .customClickable(onClick = onClick, noIndication = true),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
            Spacer(modifier = Modifier.width(drawablePadding))
            Image(
                modifier = Modifier.size(iconSize),
                painter = painterResource(id = icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = iconTint)
            )
        }
    }
}

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    iconSize: Dp = 12.dp,
    drawablePadding: Dp = 0.dp,
    iconAlignment: Alignment.Horizontal,
    content: @Composable () -> Unit
) {
    if (iconAlignment == Alignment.Start) {
        Row(
            modifier = modifier.clip(RoundedCornerShape(4.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(start = 10.dp)
                    .size(iconSize),
                painter = painterResource(id = icon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(drawablePadding))
            content()
        }
    } else if (iconAlignment == Alignment.End) {
        Row(
            modifier = modifier.clip(RoundedCornerShape(4.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
            Spacer(modifier = Modifier.width(drawablePadding))
            Image(
                modifier = Modifier.size(iconSize),
                painter = painterResource(id = icon),
                contentDescription = null
            )
        }
    }
}

@Composable
fun DropDownItem(modifier: Modifier = Modifier, title: String, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = GreyColor, shape = RoundedCornerShape(8.dp))
            .customClickable(onClick = onClick, bounded = true)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PrimaryRegularText(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 12.dp),
                text = title,
                fontSize = 12.sp,
                color = DarkGreyColor
            )
            Image(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 12.dp),
                painter = painterResource(id = R.drawable.ic_dropdown_arrow),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = DarkGreyColor)
            )
        }
    }
}