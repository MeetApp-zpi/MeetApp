<script lang="ts">
    export let clickHandler: () => void;
    export let marginX: number;
    export let marginY: number;
    export let textSize: number;
</script>

<button
    class="bg-pickle border-pine border-2 text-ivory
        text-[{textSize}px] rounded-full py-{marginY} px-{marginX} hover:opacity-90 transition ease-in-out delay-100
        focus:ring focus:ring-tea font-bold"
    on:click={clickHandler}
>
    <slot />
</button>
