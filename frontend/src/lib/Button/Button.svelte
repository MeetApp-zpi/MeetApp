<script lang="ts">
    export let clickHandler: () => void;
</script>

<button
    class="bg-pickle border-pine border-2 text-ivory
        text-xl rounded-full py-2 px-12 hover:opacity-90 transition ease-in-out delay-100
        focus:ring focus:ring-tea"
    on:click={clickHandler}
>
    <slot />
</button>
