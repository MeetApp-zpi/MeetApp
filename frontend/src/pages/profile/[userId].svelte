<script lang="ts">
    import execute from '../../lib/fetchWrapper';
    import { redirect, url } from '@roxi/routify';
    import Button from '../../lib/Button/Button.svelte';
    import Header from '../../lib/Header/Header.svelte';
    import Pill from '../../lib/Pill/Pill.svelte';
    import IoIosMail from 'svelte-icons/io/IoIosMail.svelte';

    export let userId: number;

    async function fetchUserInterests() {
        const userDetails = await execute(`users/${userId}/details`, 'GET').then(async (response) => {
            if (!response.ok) {
                $redirect('/login');
            }

            return await response.json();
        });
        const userInterests = await execute(`users/${userId}/categories`, 'GET').then(async (response) => {
            if (!response.ok) {
                $redirect('/login');
            }

            return await response.json();
        });

        return [userDetails, userInterests];
    }
</script>

<div class="h-screen bg-ivory">
    <Header />
    {#await fetchUserInterests() then [userDetails, userInterests]}
        <div class="flex flex-col items-center px-8 overflow-scroll h-[calc(100%-4rem)]">
            <div class="w-64 h-64 mr-2">
                <img class="rounded-full" src={userDetails.profilePicture} alt="Profile avatar" />
            </div>

            <div class="text-cocoa text-3xl font-bold pb-12">{userDetails.firstName} {userDetails.lastName}</div>

            <div class="flex flex-col items-center pb-12">
                <p class="text-cocoa text-xl font-bold pb-5">Zainteresowania</p>

                <div class="flex flex-wrap justify-center">
                    {#each userInterests as interest, index}
                        <Pill class="flex self-center px-4 mx-1 my-1">
                            {interest.name}
                        </Pill>
                    {/each}
                </div>
            </div>

            <a class="mb-12" href={$url('/posts')}>
                <p class="text-pine text-2xl hover:opacity-50 transition ease-in-out delay-100 cursor-pointer">Aktywne Posty</p>
            </a>

            <Button class="flex flex-row text-xl text-base px-12 py-2 mb-12">
                <div class="h-7 w-7 mr-2">
                    <IoIosMail />
                </div>
                Wyślij wiadomość
            </Button>
        </div>
    {/await}
</div>
