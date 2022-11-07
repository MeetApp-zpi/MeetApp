<script lang="ts">
    export let clickHandler: () => void;
    let cssClass: string;
    export { cssClass as class };
</script>

<button
    class="bg-pickle border-pine border-2 text-ivory
        rounded-full hover:opacity-90 transition ease-in-out delay-100
        focus:ring focus:ring-tea font-bold {cssClass}"
    on:click={clickHandler}
>
    <slot />
</button>
